package com.jx.androiddemo.testactivity.ui.u32;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U32Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View
{

    private U32T1Fragment mF0;

    private U32T1_2Fragment mF1;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        /**
         *1、打开FEATURE_CONTENT_TRANSITIONS开关(可选)，这个开关默认是打开的
         */
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject()
    {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout()
    {
        return R.layout.activity_u32;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData()
    {
        initView();
        initListener();
    }

    private void initView()
    {
        mF0 = new U32T1Fragment(this, () -> {
            showFragment1();
        });
        mF1 = new U32T1_2Fragment(this, () -> {
            showFragment0();
        });
        showFragment0();
    }

    @SuppressLint("CheckResult")
    private void initListener()
    {
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

    private void showFragment0()
    {
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .replace(R.id.layout_frame, mF0).commit();
    }

    private void showFragment1()
    {
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .addSharedElement(mF0.tv_hello1, "tv_hello1")//添加共享元素
                .replace(R.id.layout_frame, mF1).commit();
    }
}