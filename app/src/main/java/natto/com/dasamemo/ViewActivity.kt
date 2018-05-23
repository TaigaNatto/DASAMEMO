package natto.com.dasamemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class ViewActivity : AppCompatActivity() {

    var isMode=false
    var containerViewBottom:LinearLayout?=null
    var containerEditBottom:LinearLayout?=null

    var titleV:TextView?=null
    var dateV:TextView?=null
    var contentV:TextView?=null

    var editBtn:TextView?=null
    var delBtn:TextView?=null

    var saveBtn:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        titleV=findViewById(R.id.title_memo)
        dateV=findViewById(R.id.date_memo)
        contentV=findViewById(R.id.content_memo)

        titleV?.text=intent.getStringExtra("title")
        dateV?.text=intent.getIntExtra("date",0).toString()
        contentV?.text=intent.getStringExtra("content")
        isMode=intent.getBooleanExtra("mode",false)

        containerViewBottom=findViewById(R.id.container_view_mode)
        containerEditBottom=findViewById(R.id.container_edit_mode)

        modeSet()

        editBtn=findViewById(R.id.btn_edit)
        editBtn?.setOnClickListener {
            modeChange()
        }

        saveBtn=findViewById(R.id.btn_save)
        saveBtn?.setOnClickListener {
            //todo 保存処理
            modeChange()
        }
    }

    fun modeChange(){
        isMode=!isMode
        modeSet()
    }

    fun modeSet(){
        if(isMode){
            containerViewBottom?.visibility= View.GONE
            containerEditBottom?.visibility=View.VISIBLE
        }else{
            containerViewBottom?.visibility= View.VISIBLE
            containerEditBottom?.visibility=View.GONE
        }
    }
}
