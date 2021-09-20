package com.jx.androiddemo.testactivity.function.f1;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.tool.JniTest;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class F1Activity extends BaseMvpActivity<F1Presenter> implements F1Contract.View {

    @BindView(R.id.tv_current_thread)
    TextView tv_current_thread;

    @BindView(R.id.tv_jni)
    TextView tv_jni;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f1;
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
        RxView.clicks(tv_current_thread)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showMsg(Looper.getMainLooper().getThread() == Thread.currentThread() ? "yes" : "no");
                });
        RxView.clicks(tv_jni)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    JniTest j = new JniTest();
                    j.test();
                });
    }
}