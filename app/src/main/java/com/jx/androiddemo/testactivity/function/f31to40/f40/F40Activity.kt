package com.jx.androiddemo.testactivity.function.f31to40.f40

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.aidl2.F40Manage
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_f40.*
import java.util.concurrent.TimeUnit

class F40Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private var mF40Manage: F40Manage? = null

    companion object {
        private val TAG = "F40Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f40
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
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { id -> }

        //点击
        RxView.clicks(tv1)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o -> bindService1() }
        RxView.clicks(tv2)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o -> mF40Manage?.sendMsg("${System.currentTimeMillis()}") }
    }

    private fun bindService1() {
        val intent = Intent()
        intent.action = "com.jx.f40.f40service"
        intent.`package` = packageName
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(F40Activity.TAG, "onServiceConnected")
                if (service is F40Manage) {
                    mF40Manage = service
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(F40Activity.TAG, "onServiceDisconnected")
            }
        }, BIND_AUTO_CREATE)
    }
}