package natto.com.dasamemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import io.realm.Realm
import java.util.*

class MainActivity : AppCompatActivity() {

    var realm: Realm?=null

    var btnAdd: ImageView? = null
    var memoList: ListView? = null

    var memoAdapter: MemoAdapter? = null

    override fun onResume(){
        super.onResume()
        memoAdapter?.let {
            loadRealm()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)

        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
        }

        setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        realm= Realm.getDefaultInstance()

        memoList = findViewById(R.id.memo_list)
        memoAdapter = MemoAdapter(this)
//        val sample = arrayOf(
//                MemoModel("始祖鳥は電気羊の夢を見るか", 0, "むかしむかしあるところに"),
//                MemoModel("test", 0, "aa"),
//                MemoModel("神とは何か", 0, "全知全能"),
//                MemoModel("テスト日程", 0, "数学"),
//                MemoModel("始祖鳥は電気羊の夢を見るか", 0, "むかしむかしあるところに"),
//                MemoModel("test", 0, "aa"),
//                MemoModel("神とは何か", 0, "全知全能"),
//                MemoModel("テスト日程", 0, "数学"))
//        for (item in sample) {
//            memoAdapter?.add(item)
//        }
        memoList?.adapter = memoAdapter
        memoList?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, pos, l ->
            val intent = Intent(this, ViewActivity::class.java)
            val transitionName = "trans_view"
            val transView = view.findViewById<LinearLayout>(R.id.container_memo)

            val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            transView,   // 遷移がはじまるビュー
                            transitionName    // 遷移先のビューの transitionName
                    )

            memoAdapter?.getItem(pos)?.let {
                intent.putExtra("title", it.title)
                intent.putExtra("date", it.date?.time)
                intent.putExtra("content", it.content)
                intent.putExtra("mode",R.integer.flg_edit_memo)
            }

            startActivity(intent, options.toBundle())
        }
        memoList?.setSelection(memoAdapter?.count!!)

        btnAdd = findViewById(R.id.btn_add)
        btnAdd?.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, ViewActivity::class.java)
            val transitionName = "trans_edit"
            val transView = btnAdd

            val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            transView!!,   // 遷移がはじまるビュー
                            transitionName    // 遷移先のビューの transitionName
                    )

                intent.putExtra("title", "無題")
                intent.putExtra("date", 0L)
                intent.putExtra("content", "")
            intent.putExtra("mode",R.integer.flg_new_memo)

            startActivity(intent, options.toBundle())
        })

        loadRealm()
    }

    fun loadRealm(){
        //todo realmのロード
        var memoResults = realm!!.where(MemoModel::class.java).findAll()
        memoAdapter?.clear()
        for (memoItem in memoResults){
            memoAdapter?.add(memoItem)
        }
        memoList?.adapter=memoAdapter
    }
}
