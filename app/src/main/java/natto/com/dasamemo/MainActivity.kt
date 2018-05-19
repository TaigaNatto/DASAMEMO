package natto.com.dasamemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    var btnAdd:ImageView?=null
    var memoList:ListView?=null

    var memoAdapter:MemoAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memoList=findViewById(R.id.memo_list)
        memoAdapter= MemoAdapter(this)
        val sample= arrayOf(
                MemoModel("始祖鳥は電気羊の夢を見るか",0,"むかしむかしあるところに"),
                MemoModel("test",0,"aa"),
                MemoModel("神とは何か",0,"全知全能"),
                MemoModel("テスト日程",0,"数学"))
        for (item in sample){
            memoAdapter?.add(item)
        }
        memoList?.adapter=memoAdapter

        btnAdd=findViewById(R.id.btn_add)
        btnAdd?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,ViewActivity::class.java)
            val transitionName = "trans_add"

            val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            btnAdd!!,   // 遷移がはじまるビュー
                            transitionName    // 遷移先のビューの transitionName
                    )
            startActivity(intent, options.toBundle())
        })
    }
}
