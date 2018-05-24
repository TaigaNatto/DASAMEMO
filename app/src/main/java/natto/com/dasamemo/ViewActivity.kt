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


class ViewActivity : AppCompatActivity() {

    var isMode = false
    var containerViewBottom: LinearLayout? = null
    var containerEditBottom: LinearLayout? = null

    var titleV: EditText? = null
    var dateV: TextView? = null
    var contentV: EditText? = null

    var editBtn: TextView? = null
    var delBtn: TextView? = null

    var saveBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        titleV = findViewById(R.id.title_memo)
        dateV = findViewById(R.id.date_memo)
        contentV = findViewById(R.id.content_memo)

        titleV?.setText(intent.getStringExtra("title"))
        dateV?.text = intent.getIntExtra("date", 0).toString()
        contentV?.setText(intent.getStringExtra("content"))
        isMode = intent.getBooleanExtra("mode", false)

        containerViewBottom = findViewById(R.id.container_view_mode)
        containerEditBottom = findViewById(R.id.container_edit_mode)

        if(isMode) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        modeSet()

        editBtn = findViewById(R.id.btn_edit)
        editBtn?.setOnClickListener {
            modeChange()
        }

        saveBtn = findViewById(R.id.btn_save)
        saveBtn?.setOnClickListener {
            //todo 保存処理
            modeChange()
        }
    }

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
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(contentV, InputMethodManager.SHOW_FORCED)
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

        }
    }
}
