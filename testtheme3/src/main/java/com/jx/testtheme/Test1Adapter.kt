package com.jx.testtheme

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jx.testtheme3.R

class Test1Adapter(val mContext: Context) : RecyclerView.Adapter<Test1Adapter.ViewHolder>() {
    private var mContentList = ArrayList<ItemBean>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<ItemBean>?) {
        mContentList.clear()
        list?.let { mContentList.addAll(list) }
    }

    fun getData(): ArrayList<ItemBean> = mContentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_test1, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContentList[position].apply {
            holder.itemView.findViewById<TextView>(R.id.tv).text = "$data ${this@Test1Adapter.hashCode()}"
        }
    }

    override fun getItemCount(): Int = mContentList.size
}