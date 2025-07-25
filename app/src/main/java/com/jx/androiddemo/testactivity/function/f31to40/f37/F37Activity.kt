package com.jx.androiddemo.testactivity.function.f31to40.f37;

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
import java.util.concurrent.TimeUnit

class F37Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val tv_test1 by lazy { findViewById<View>(R.id.tv_test1) }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f37
    }

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        RxView.clicks(tv_test1)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                Animal::eat.annotations.forEach {
                    if (it is LocalAnnotation) {
                        Log.d("F37Activity", "${it.name} ${it.desc}")
                    }
                }
                Log.d(
                    "F37Activity",
                    "${Animal::class.java.getAnnotation(LocalAnnotation::class.java).name}"
                )
            }
    }
}