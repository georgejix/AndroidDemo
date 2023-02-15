package com.jx.androiddemo.testactivity.ui.ui31to40.ui37

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jx.androiddemo.R
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.item_u37.view.*

class U37Adapter(val mContext: Context, val mListener: Listener?) :
    RecyclerView.Adapter<U37Adapter.ViewHolder>() {
    private var mContentList = ArrayList<String>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<String>?) {
        mContentList.clear()
        list?.let { mContentList.addAll(list) }
    }

    fun getData(): ArrayList<String> = mContentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_u37, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContentList[position].apply {
            holder.itemView.tv_text.text = this
            holder.itemView.setOnClickListener {
                if (ClickListenerUtil.canClick()) {
                    mListener?.onItemClick(position, this)
                }
            }
        }
    }

    override fun getItemCount(): Int = mContentList.size

    interface Listener {
        fun onItemClick(position: Int, bean: String)
    }
}