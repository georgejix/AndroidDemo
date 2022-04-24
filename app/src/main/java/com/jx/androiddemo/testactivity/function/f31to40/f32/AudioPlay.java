package com.jx.androiddemo.testactivity.function.f31to40.f32;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class AudioPlay {
    private final String TAG = "AudioPlay";
    private static final AudioPlay mAudioPlay = new AudioPlay();
    private final MediaPlayer mPlayer;
    private MediaPlayer mUtilPlayer;
    private final AtomicBoolean mIsPlaying = new AtomicBoolean(false);
    private Listener mListener;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final int REFRESH_TIME = 1001;

    private AudioPlay() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(mp -> {
            Log.d(TAG, "准备好了");
            mPlayer.start();
            playStatusChange(true);
            durationChange(mPlayer.getDuration());
        });
        mPlayer.setOnCompletionListener(mp -> {
            Log.d(TAG, "播放");
            if (mPlayer.getCurrentPosition() == mPlayer.getDuration()) {
                Log.d(TAG, "播放完成");
                mPlayer.reset();
                playStatusChange(false);
                currentDurationChange(0);
            }
        });
        mPlayer.setOnSeekCompleteListener(mp -> Log.d(TAG, "onSeekComplete"));
        mHandlerThread = new HandlerThread("audioHandler");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case REFRESH_TIME:
                        currentDurationChange(mPlayer.getCurrentPosition());
                        mHandler.sendEmptyMessageDelayed(REFRESH_TIME, 500);
                        break;
                }
            }
        };
    }

    public static AudioPlay getInstance() {
        return mAudioPlay;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void playPause(String path, Integer seekPosition) {
        mHandler.post(() -> {
            if (0 == mPlayer.getDuration()) {
                //未加载数据
                try {
                    mPlayer.reset();
                    mPlayer.setDataSource(path);
                    mPlayer.prepare();
                } catch (Exception e) {
                }
            } else if (!mIsPlaying.get()) {
                //暂停中
                mPlayer.start();
                playStatusChange(true);
            } else if (mIsPlaying.get()) {
                //播放中
                if (null == seekPosition) {
                    mPlayer.pause();
                    playStatusChange(false);
                }
            }
            if (null != seekPosition) {
                mPlayer.seekTo(mPlayer.getDuration() - seekPosition < 1000 ? mPlayer.getDuration() : seekPosition);
            }
        });
    }

    //退出pause调用
    public void pause() {
        mPlayer.pause();
        playStatusChange(false);
    }

    //退出页面调用
    public void quit() {
        mPlayer.stop();
        mPlayer.reset();
        playStatusChange(false);
    }

    //退出app调用
    public void release() {
        playStatusChange(false);
        mPlayer.release();
        mHandlerThread.quitSafely();
    }

    //时长状态变更
    private void durationChange(int duration) {
        if (null != mListener) {
            mListener.onDurationChange(duration);
        }
    }

    //当前时长状态变更
    private void currentDurationChange(int duration) {
        if (null != mListener) {
            mListener.onCurrentDurationChange(duration);
        }
    }

    //播放状态变更
    private void playStatusChange(boolean isPlay) {
        mIsPlaying.set(isPlay);
        if (null != mListener) {
            mListener.onPlayStatusChange(isPlay);
        }
        if (isPlay) {
            mHandler.removeMessages(REFRESH_TIME);
            mHandler.sendEmptyMessageDelayed(REFRESH_TIME, 300);
        } else {
            mHandler.removeMessages(REFRESH_TIME);
        }
    }

    //获取音频总时长
    @SuppressLint("SetTextI18n")
    public void getAudioTotalDuration(String path, SeekBar seekBar, TextView textView) {
        mHandler.post(() -> {
            if (null == mUtilPlayer) {
                mUtilPlayer = new MediaPlayer();
            }
            int duration = 0;
            try {
                mUtilPlayer.reset();
                mUtilPlayer.setDataSource(path);
                mUtilPlayer.prepare();
                duration = mUtilPlayer.getDuration();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            int finalDuration = duration;
            if (null != seekBar) {
                seekBar.setMax(finalDuration);
            }
            if (null != textView) {
                textView.post(() -> textView.setText(Integer.toString(finalDuration)));
            }
        });
    }

    public interface Listener {
        void onDurationChange(int duration);

        void onCurrentDurationChange(int duration);

        void onPlayStatusChange(boolean isPlay);
    }
}
