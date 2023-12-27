package com.jx.androiddemo.testactivity.function.f31to40.f39

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jx.androiddemo.R
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.item_f39.view.*

class F39Adapter(val mContext: Context, val mListener: Listener?) :
    RecyclerView.Adapter<F39Adapter.ViewHolder>() {
    private var mContentList = ArrayList<ListBean>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<ListBean>?) {
        mContentList.clear()
        list?.let { mContentList.addAll(list) }
    }

    fun getData(): ArrayList<ListBean> = mContentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_f39, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContentList[position].apply {
            holder.itemView.tv_content.text = this.name
            holder.itemView.setOnClickListener {
                if (ClickListenerUtil.canClick()) {
                    mListener?.onItemClick(position, this)
                }
            }
        }
    }

    override fun getItemCount(): Int = mContentList.size

    interface Listener {
        fun onItemClick(position: Int, bean: ListBean)
    }
}