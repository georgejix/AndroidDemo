package com.jx.testtheme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jx.testtheme5.R
import com.jx.testtheme5.databinding.ItemTest1Binding

class Test1Adapter(val mContext: Context) : RecyclerView.Adapter<Test1Adapter.ViewHolder>() {
    private var mContentList = ArrayList<ItemBean>()
    private var mBinding: ItemTest1Binding? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<ItemBean>?) {
        mContentList.clear()
        list?.let { mContentList.addAll(list) }
    }

    fun getData(): ArrayList<ItemBean> = mContentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTest1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).also {
                mBinding = it
                mBinding?.gvm = GlobalViewModel
            }.root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mContentList[position].apply {
            holder.itemView.findViewById<TextView>(R.id.tv).text = "$data ${this@Test1Adapter.hashCode()}"
        }
    }

    override fun getItemCount(): Int = mContentList.size
}