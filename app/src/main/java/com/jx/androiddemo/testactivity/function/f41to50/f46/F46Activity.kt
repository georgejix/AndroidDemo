package com.jx.androiddemo.testactivity.function.f41to50.f46

import android.annotation.SuppressLint
import android.location.GnssStatus
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import java.io.File
import java.util.concurrent.TimeUnit


class F46Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val TAG = javaClass.simpleName
    private val tv_copy by lazy { findViewById<View>(R.id.tv_copy) }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f46
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
        RxView.clicks(tv_copy)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                copy()
            }
    }

    private fun copy() {
        getExternalFilesDir("file")?.let { F46AssetsHelper.copyAssetsToDevice(it) }
    }

}