package natto.com.dasamemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class MemoAdapter(context: Context) : BaseAdapter() {
    private var mInflater: LayoutInflater?=null
    private var mItemList: ArrayList<MemoModel>?=null

    init {
        //レイアウトxmlから、viewを生成
        mInflater = context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mItemList= ArrayList()
    }

    internal class ViewHolder(view: View) {
        var titleTextV: TextView =view.findViewById(R.id.title_memo_item)
        var dateTextV: TextView = view.findViewById(R.id.date_memo_item)
        var contTextV: TextView = view.findViewById(R.id.content_memo_item)
    }

    fun add(item:MemoModel){
        this.mItemList!!.add(item)
    }

    override fun getCount(): Int {
        return mItemList!!.size
    }

    override fun getItem(position: Int): MemoModel {
        return mItemList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        var convertView = convertView

        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater!!.inflate(R.layout.item_memo_list, null)
            holder = ViewHolder(convertView)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.titleTextV.text=mItemList!![position].title
        holder.dateTextV.text = mItemList!![position].date.toString()
        holder.contTextV.text=mItemList!![position].content

        return convertView
    }
}