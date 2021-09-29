package com.jx.androiddemo.testactivity.function.f21;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F21Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private static final String TAG = "MediaPlayerActivity";
    private MediaPlayer mMediaPlayer;
    private SoundPool soundPool;
    private String audioPath1, audioPath2;

    private LinkedBlockingQueue<String> mMediaPlayerQueue;
    private PlayAudioThread mPlayAudioThread;
    private MediaPlayer mediaPlayer;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f21;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        audioPath1 = getAssets() + File.separator + "fcw_h_be.wav";
        audioPath2 = getAssets() + File.separator + "fcw_h_be.wav";
        if (null == soundPool) {
            soundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        }
        soundId = soundPool.load(audioPath2, 1);
        mPlayAudioThread = new PlayAudioThread();
        mPlayAudioThread.start();
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

    public void play1(View view) {
        /*if(null != mediaPlayer && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(audioPath1)));
        mediaPlayer.start();*/
        if (null == mMediaPlayerQueue)
            return;
        try {
            mMediaPlayerQueue.offer(audioPath1, 10 * 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int soundId;
    int streamid = -1;

    public void play2(View view) {
        if (-1 == streamid) {
            streamid = soundPool.play(soundId, 1, 1, 1, -1, 1);
        } else {
            soundPool.stop(streamid);
            streamid = -1;
        }
    }

    class PlayAudioThread extends Thread {
        @Override
        public void run() {
            if (null == mMediaPlayerQueue) {
                mMediaPlayerQueue = new LinkedBlockingQueue<String>();
            }
            String path;
            while (!isInterrupted()) {
                try {
                    path = mMediaPlayerQueue.take();
                    if (null == path)
                        continue;
                    while (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
                        SystemClock.sleep(200);
                    }
                    if (null != mMediaPlayer) {
                        mMediaPlayer.release();
                        Log.d(TAG, "release");
                    }
                    mMediaPlayer = MediaPlayer.create(F21Activity.this,
                            Uri.fromFile(new File(path)));
                    mMediaPlayer.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}