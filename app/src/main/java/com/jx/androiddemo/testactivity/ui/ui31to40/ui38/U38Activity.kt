package com.jx.androiddemo.testactivity.ui.ui31to40.ui38

import android.annotation.SuppressLint
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.android.synthetic.main.activity_u38.tv_anim
import java.util.concurrent.TimeUnit

class U38Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    companion object {
        val TAG = "U38Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int = R.layout.activity_u38

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
        RxView.clicks(tv_anim)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { v -> startAnim() }
    }

    private fun startAnim() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.u38_anim_in)
        tv_anim.startAnimation(anim)
    }

}