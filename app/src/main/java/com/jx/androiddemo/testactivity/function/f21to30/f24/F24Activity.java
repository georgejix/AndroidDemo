package com.jx.androiddemo.testactivity.function.f21to30.f24;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F24Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View,
        SurfaceHolder.Callback {
    private final static String TAG = "VideoViewActivity";

    @BindView(R.id.surfaceView)
    SurfaceView mSurfaceView;

    private String video1 = "https://img.1000.com/qm-a-img/prod/2775370/967d971876916b079401f6761d1de54f.mp4";

    private String video2 = "https://img.1000.com/qm-a-img/prod/2775370/89fddf6be17ffde4b77f308bbe5eaf11.mp4";

    private String videoUrl[] = new String[2];

    private int playIndex = 0;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f24;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
        playVideo();
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
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    private void playVideo() {
        videoUrl[0] = video1;
        videoUrl[1] = video2;
        mMediaPlayer = new MediaPlayer();
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.setOnPreparedListener(mp -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSurfaceView.getLayoutParams();
            params.width = mMediaPlayer.getVideoWidth();
            params.height = mMediaPlayer.getVideoHeight();
            mSurfaceView.setLayoutParams(params);
            Log.d(TAG, mMediaPlayer.getVideoWidth() + "," + mMediaPlayer.getVideoHeight());
            mMediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mp -> {
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(videoUrl[playIndex++ % 2]);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            mMediaPlayer.setDataSource(videoUrl[playIndex++ % 2]);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        if (null != mMediaPlayer) {
            mMediaPlayer.reset();
        }
        super.onDestroy();
    }
}