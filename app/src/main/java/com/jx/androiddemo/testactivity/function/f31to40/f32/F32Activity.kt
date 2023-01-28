package com.jx.androiddemo.testactivity.function.f31to40.f32

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.activity_f32.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class F32Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f32
    }

    override fun initEventAndData() {
        initView()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        val views = arrayListOf<TextView>(tv_1, tv_2, tv_3)
        var num = 0
        while (num < views.size) {
            var i = num
            views[num].setOnClickListener { v -> Log.d(TAG, "${i}  ${num}") }
            num++
        }
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
        }
    }
}