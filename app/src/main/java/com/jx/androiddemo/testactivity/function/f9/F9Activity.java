package com.jx.androiddemo.testactivity.function.f9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F9Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_intent_service)
    TextView tv_intent_service;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f9;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
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
        RxView.clicks(tv_intent_service)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    Intent rssPullServiceIntent = new Intent(this, RSSPullService.class);
                    rssPullServiceIntent.setData(Uri.parse("www.baidu.com"));
                    startService(rssPullServiceIntent);
                });
    }
}