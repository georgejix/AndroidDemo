package com.jx.androiddemo.testactivity.function.f31to40.f32;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;
import com.jx.androiddemo.tool.ClickListenerUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F32Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.seekbar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_current_time)
    TextView mCurrentTimeTv;
    @BindView(R.id.tv_total_time)
    TextView mTotalTimeTv;
    @BindView(R.id.img_play_pause)
    ImageView mPlayPauseImg;

    //private String mPath1 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220411173608441.wav";
    private String mPath2 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220331174043925.aac";
    private String mPath3 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220222095449261.aac";
    private String mPath1 = "";

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

    @SuppressLint({"SetTextI18n", "CheckResult"})
    private void initView() {
        mPath1 = getExternalCacheDir() + File.separator + "f30.wav";
        AudioPlay.getInstance().setListener(new AudioPlay.Listener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDurationChange(int duration) {
                postUi(() -> {
                    mTotalTimeTv.setText(Integer.toString(duration));
                    mSeekBar.setMax(duration);
                });
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCurrentDurationChange(int duration) {
                postUi(() -> {
                    mCurrentTimeTv.setText(Integer.toString(duration));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mSeekBar.setProgress(duration, true);
                    } else {
                        mSeekBar.setProgress(duration);
                    }
                });
            }

            @Override
            public void onPlayStatusChange(boolean isPlay) {
                postUi(() -> {
                    mPlayPauseImg.setImageResource(isPlay ? R.mipmap.img_audio_pause : R.mipmap.img_audio_play);
                });
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                AudioPlay.getInstance().pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AudioPlay.getInstance().playPause(mPath1, seekBar.getProgress());
            }
        });
        AudioPlay.getInstance().getAudioTotalDuration(mPath1, mSeekBar, mTotalTimeTv);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioPlay.getInstance().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlay.getInstance().quit();
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
            case R.id.img_play_pause:
                AudioPlay.getInstance().playPause(mPath1, null);
                break;
        }
    }

    private void postUi(Runnable runnable) {
        if (null == mPlayPauseImg || null == runnable) {
            return;
        }
        mPlayPauseImg.post(runnable);
    }
}