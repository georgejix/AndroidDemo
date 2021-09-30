package com.jx.androiddemo.testactivity.function.f25;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.widget.VideoView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F25Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.videoView)
    VideoView mVideoView;

    private String video1 = "https://img.1000.com/qm-a-img/prod/2775370/967d971876916b079401f6761d1de54f.mp4";

    private String video2 = "https://img.1000.com/qm-a-img/prod/2775370/89fddf6be17ffde4b77f308bbe5eaf11.mp4";

    private String videoUrl[] = new String[2];

    private int playIndex = 0;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f25;
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
        mVideoView.setVideoURI(Uri.parse(videoUrl[playIndex++ % 2]));
        mVideoView.setOnPreparedListener(mp -> {
            mVideoView.start();
        });
        mVideoView.setOnCompletionListener(mp -> {
            mVideoView.setVideoURI(Uri.parse(videoUrl[playIndex++ % 2]));
        });
    }

    @Override
    protected void onDestroy() {
        if (null != mVideoView) {
            mVideoView.suspend();
        }
        super.onDestroy();
    }
}