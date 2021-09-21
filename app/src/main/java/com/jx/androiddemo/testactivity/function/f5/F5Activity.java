package com.jx.androiddemo.testactivity.function.f5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F5Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "F5Activity";

    private ViewHandler viewHandler;
    private CountDownLatch countDownLatch;
    private int i = 0;

    private final int MESSAGE1 = 1001;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f5;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        if (null == viewHandler) {
            viewHandler = new ViewHandler();
        }
        countDownLatch = new CountDownLatch(1);
        for (i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        //Message msg = new Message();
                        Message msg = viewHandler.obtainMessage();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("num", i);
                        msg.setData(bundle);
                        Log.d(TAG, "before sendMessage");
                        countDownLatch.await();
                        //Thread.sleep(200);
                        Log.d(TAG, "sendMessage");
                        viewHandler.sendMessage(msg);
                        /*Message msg = viewHandler.obtainMessage();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("num", i);
                        msg.setData(bundle);
                        viewHandler.removeMessages(MESSAGE1);
                        viewHandler.sendMessageDelayed(msg, 1000);*/
                    } catch (Exception e) {
                        Log.d(TAG, e + "");
                    }
                }
            }).start();
        }

        countDownLatch = new CountDownLatch(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Message msg = new Message();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("2num", i);
                        msg.setData(bundle);
                        Log.d(TAG, "2before sendMessage");
                        countDownLatch.await();
                        //Thread.sleep(200);
                        Log.d(TAG, "2sendMessage");
                        viewHandler.sendMessage(msg);
                    } catch (Exception e) {
                        Log.d(TAG, e + "");
                    }
                }
            }).start();
        }

        countDownLatch.countDown();
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

    }

    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE1:
                    //removeMessages(MESSAGE1);
                    /*try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    Log.d(TAG, "num=" + msg.getData().getInt("num"));
                    break;
            }
        }
    }
}