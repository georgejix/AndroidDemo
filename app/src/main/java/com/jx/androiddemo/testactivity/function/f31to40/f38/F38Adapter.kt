package com.jx.androiddemo.testactivity.function.f31to40.f38

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jx.androiddemo.R
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.item_f39.view.*

class F38Adapter(val mContext: Context) :
    RecyclerView.Adapter<F38Adapter.ViewHolder>() {
    private var mContentList = ArrayList<F38ListBean>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<F38ListBean>?) {
        mContentList.clear()
        list?.let { mContentList.addAll(list) }
    }

    fun getData(): ArrayList<F38ListBean> = mContentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_f38, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContentList[position].apply {
            holder.itemView.tv_content.text = this.mName
            holder.itemView.setOnClickListener {
                if (ClickListenerUtil.canClick(50)) {
                    f()
                }
            }
        }
    }

    override fun getItemCount(): Int = mContentList.size

    interface Listener {
        fun onItemClick(position: Int, bean: F38ListBean)
    }
}