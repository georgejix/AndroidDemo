package com.jx.androiddemo.testactivity.function.f31to40.f32;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;
import com.jx.androiddemo.tool.ClickListenerUtil;
import com.jx.androiddemo.tool.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F32Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    private MediaPlayer mPlayer;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f32;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(mp -> {
            Log.d(TAG, "准备好了");
        });
        mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.d(TAG, "缓冲进度：" + percent);
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "播放完成");

            }
        });
        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                Log.d(TAG, "onSeekComplete");
            }
        });
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

    public void onClick(View view) {
        if (!ClickListenerUtil.canClick()) return;
        switch (view.getId()) {
            case R.id.tv_load:
                ToastUtil.showTextToast("load");
                try {
                    mPlayer.setDataSource("https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220411173608441.wav");
                    mPlayer.prepare();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                break;
            case R.id.tv_play:
                ToastUtil.showTextToast("play");
                mPlayer.start();
                break;
            case R.id.tv_pause:
                ToastUtil.showTextToast("pause");
                mPlayer.pause();
                break;
            case R.id.tv_resume:
                ToastUtil.showTextToast("resume");
                mPlayer.start();
                break;
            case R.id.tv_stop:
                ToastUtil.showTextToast("stop");
                mPlayer.stop();
                mPlayer.reset();
                break;
            case R.id.tv_release:
                ToastUtil.showTextToast("release");
                mPlayer.release();
                initMediaPlayer();
                break;
        }
    }
}