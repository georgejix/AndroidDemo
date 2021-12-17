package com.jx.androiddemo.testactivity.ui.u31;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class U31Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_test1)
    TextView tv_test1;
    @BindView(R.id.tv_test2)
    TextView tv_test2;
    @BindView(R.id.tv_test3)
    TextView tv_test3;
    @BindView(R.id.tv_test4)
    TextView tv_test4;
    @BindView(R.id.tv_test5)
    TextView tv_test5;
    @BindView(R.id.tv_test6)
    TextView tv_test6;
    @BindView(R.id.tv_test7)
    TextView tv_test7;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u31;
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
        RxView.clicks(tv_test1)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test1Activity.class));
                });
        RxView.clicks(tv_test2)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test2Activity.class));
                });
        RxView.clicks(tv_test3)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test3Activity.class));
                });
        RxView.clicks(tv_test4)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test4Activity.class));
                });
        RxView.clicks(tv_test5)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test5Activity.class));
                });
        RxView.clicks(tv_test6)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(mContext, U31Test6Activity.class));
                });
        RxView.clicks(tv_test7)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    //https://www.jianshu.com/p/fa1c8deeaa57
                    startActivity(new Intent(mContext, U31Test7Activity.class));
                });
    }
}