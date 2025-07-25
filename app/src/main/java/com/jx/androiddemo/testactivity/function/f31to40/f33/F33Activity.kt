package com.jx.androiddemo.testactivity.function.f31to40.f33

import android.view.View
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil

class F33Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    val mF33Network: F33Network by lazy { F33Network() }
    private val tv_send5 by lazy { findViewById<View>(R.id.tv_send5) }
    private val tv_send10 by lazy { findViewById<View>(R.id.tv_send10) }

    companion object {
        val TAG = "F33Activity"
    }

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f33
    }

    override fun initEventAndData() {
        initView()
    }

    private fun initView() {
        tv_send5.setOnClickListener(this::onClick)
        tv_send10.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
            R.id.tv_send5 -> send(5)
            R.id.tv_send10 -> send(10)
        }
    }

    private fun send(num: Int) {
        var index = num
        while (index-- > 0) {
            mF33Network.execute {
                mF33Network.sendPost()
            }
        }
    }
}