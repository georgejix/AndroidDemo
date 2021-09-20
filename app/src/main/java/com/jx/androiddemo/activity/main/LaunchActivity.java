package com.jx.androiddemo.activity.main;

import android.annotation.SuppressLint;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Navigator;
import com.jx.androiddemo.contract.main.LaunchContract;
import com.jx.androiddemo.presenter.main.LaunchPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LaunchActivity extends BaseMvpActivity<LaunchPresenter> implements LaunchContract.View {
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launch;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Navigator.navigateToMainActivity(this);
                });
    }
}