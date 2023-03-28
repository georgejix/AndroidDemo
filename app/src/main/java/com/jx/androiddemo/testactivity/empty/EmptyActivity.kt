package com.jx.androiddemo.testactivity.empty

import android.annotation.SuppressLint
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class EmptyActivity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_empty
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
        /*RxView.clicks(null)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o -> }*/
    }
}