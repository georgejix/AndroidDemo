package com.jx.androiddemo.testactivity.function.f14;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F14Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "TimerActivity";

    private ViewHandler viewHandler;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f14;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        //t1();
        t2();
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

    private void t2(){
        final CountDownTimer timer = new CountDownTimer(5000 + 500, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer.cancel();
            }
        }).start();
    }


    MyTimerTask timerTask;
    private void t1(){
        final Timer timer = new Timer();
        timerTask = new MyTimerTask();
        final TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                if(null != viewHandler){
                    //viewHandler.sendEmptyMessage(TIMER1);
                }
                Log.d(TAG, "timer2");
            }
        };
        timer.schedule(timerTask,1000,500);//延时1s，每隔500毫秒执行一次run方法

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timerTask.cancel();
                timerTask = new MyTimerTask();
                timer.schedule(timerTask,1000,500);//延时1s，每隔500毫秒执行一次run方法
            }
        }).start();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.d(TAG, "timer1");
        }
    }

    private final int TIMER1 = 1001;
    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TIMER1:
                    Toast.makeText(F14Activity.this, "timer1 " +
                            System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}