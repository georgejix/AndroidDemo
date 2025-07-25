package com.jx.androiddemo.testactivity.ui.ui31to40.ui37

import android.annotation.SuppressLint
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter

class U37Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private var mU37Adapter: U37Adapter? = null
    private var mItemTouchHelper: ItemTouchHelper? = null
    private val rv by lazy { findViewById<RecyclerView>(R.id.rv) }

    companion object {
        val TAG = "U37Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int = R.layout.activity_u37

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
        mU37Adapter = U37Adapter(mContext, object : U37Adapter.Listener {
            override fun onItemClick(position: Int, bean: String) {
            }
        })
        val data = ArrayList<String>()
        (1..20).forEach { data.add(it.toString()) }
        mU37Adapter?.setData(data)
        rv.adapter = mU37Adapter
        mItemTouchHelper = ItemTouchHelper(U37AdapterHelper(mU37Adapter!!))
        mItemTouchHelper?.attachToRecyclerView(rv)
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
    }

}