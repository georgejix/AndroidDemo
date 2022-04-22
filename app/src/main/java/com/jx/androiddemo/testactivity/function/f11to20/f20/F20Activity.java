package com.jx.androiddemo.testactivity.function.f11to20.f20;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F20Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final static String TAG = "Testsp";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f20;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        TestSharedPreference testSharedPreference = new TestSharedPreference(this);
        testSharedPreference.saveSomeSP();
        String sp = testSharedPreference.generateSPJsonString();
        Log.d(TAG, sp);
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

        //点击
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }
}