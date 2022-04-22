package com.jx.androiddemo.testactivity.function.f11to20.f19;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F19Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "TestHandlerActivity2";

    @BindView(R.id.handler1)
    View view_handler1;
    @BindView(R.id.handler2)
    View view_handler2;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f19;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
        testHandler();
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
        RxView.clicks(view_handler1)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    if (null != handler1) {
                        handler1.sendEmptyMessage(1001);
                    }
                });
        RxView.clicks(view_handler2)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    if (null != handler1) {
                        handler1.sendEmptyMessage(1002);
                    }
                });
    }

    Handler handler1;

    private void testHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, msg.what + "");
                        Looper.myLooper().quit();
                    }
                };
                Looper.loop();
                Log.d(TAG, "loop end");
            }
        }).start();
    }
}