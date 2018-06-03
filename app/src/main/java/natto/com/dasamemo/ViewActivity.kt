package natto.com.dasamemo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
import android.support.v4.content.ContextCompat.getSystemService
import android.view.KeyEvent
import io.realm.Realm
import java.util.*


class ViewActivity : AppCompatActivity() {

    var realm:Realm?=null

    var viewMode=0

    var isMode = false
    var containerViewBottom: LinearLayout? = null
    var containerEditBottom: LinearLayout? = null

    var memo:MemoModel?=null

    var titleV: EditText? = null
    var dateV: TextView? = null
    var contentV: EditText? = null

    var editBtn: TextView? = null
    var delBtn: TextView? = null

    var saveBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        Realm.init(this)
        realm= Realm.getDefaultInstance()

        titleV = findViewById(R.id.title_memo)
        dateV = findViewById(R.id.date_memo)
        contentV = findViewById(R.id.content_memo)

        memo= MemoModel(
                intent.getStringExtra("title"),
                Date(intent.getLongExtra("date", 0L)),
                intent.getStringExtra("content"))

        titleV?.setText(memo?.title)
        contentV?.setText(memo?.content)
        viewMode=intent.getIntExtra("mode",R.integer.flg_new_memo)
        if(viewMode==R.integer.flg_new_memo){
            isMode=true
            dateV?.text = ""
        }else if(viewMode==R.integer.flg_edit_memo){
            isMode=false
            dateV?.text = Unit().generateDateToStringType(memo?.date!!)
        }

        containerViewBottom = findViewById(R.id.container_view_mode)
        containerEditBottom = findViewById(R.id.container_edit_mode)

        modeSet()

        editBtn = findViewById(R.id.btn_edit)
        editBtn?.setOnClickListener {
            modeChange()
        }

        delBtn=findViewById(R.id.btn_delete)
        delBtn?.setOnClickListener {
            realm!!.beginTransaction()
            val memoResult = realm!!.where(MemoModel::class.java).equalTo("date",memo?.date).findFirst()
            memoResult.deleteFromRealm()
            realm!!.commitTransaction()
            finish()
        }

        saveBtn = findViewById(R.id.btn_save)
        saveBtn?.setOnClickListener {
            //todo 保存処理
            modeChange()
            realm = Realm.getDefaultInstance()
            realm!!.beginTransaction()
            if(viewMode==R.integer.flg_new_memo) {
                val newMemo = realm!!.createObject(MemoModel::class.java) // 新たなオブジェクトを作成
                newMemo.title = titleV?.text.toString()
                newMemo.content = contentV?.text.toString()
                val date = Date(System.currentTimeMillis())
                newMemo.date = date
                viewMode=R.integer.flg_edit_memo
                memo= MemoModel(newMemo.title!!, newMemo.date!!, newMemo.content!!)
            }else if(viewMode==R.integer.flg_edit_memo){
                val memoResult = realm!!.where(MemoModel::class.java).equalTo("date", memo?.date).findFirst()
                memoResult.title=titleV?.text.toString()
                memoResult.content=contentV?.text.toString()
                memo=MemoModel(memoResult.title!!,memo?.date!!,memoResult.content!!)
            }
            realm!!.commitTransaction()
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish()
//            return true
//        }
//        return false
//    }

    fun modeChange() {
        isMode = !isMode
        modeSet()
    }

    fun modeSet() {
        if (isMode) {
            containerViewBottom?.visibility = View.GONE
            containerEditBottom?.visibility = View.VISIBLE
            titleV?.isFocusable = true
            titleV?.isFocusableInTouchMode = true
            titleV?.isEnabled = true
            contentV?.isFocusable = true
            contentV?.isFocusableInTouchMode = true
            contentV?.isEnabled = true
            contentV?.requestFocus()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(contentV, InputMethodManager.SHOW_FORCED)
            contentV?.setSelection(contentV?.text?.length!!)
        } else {
            containerViewBottom?.visibility = View.VISIBLE
            containerEditBottom?.visibility = View.GONE
            titleV?.isFocusable = false
            titleV?.isFocusableInTouchMode = false
            titleV?.isEnabled = false
            contentV?.isFocusable = false
            contentV?.isFocusableInTouchMode = false
            contentV?.isEnabled = false
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}
