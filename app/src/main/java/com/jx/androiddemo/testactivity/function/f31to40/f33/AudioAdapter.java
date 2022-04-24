package com.jx.androiddemo.testactivity.function.f31to40.f33;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.f31to40.f32.AudioPlay;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class AudioAdapter extends CommonAdapter<String> {

    private final String TAG = "AudioAdapter";
    private int mShowPosition = -1;
    private View mAudioView;
    private TextView mCurrentTv, mTotalTv;
    private SeekBar mSeekBar;
    private ImageView mImg;
    private Handler mHandler;

    @Inject
    public AudioAdapter(Context context) {
        super(context, R.layout.item_f33_audio);
    }

    public void init() {
        mHandler = new Handler(Looper.getMainLooper());
        mAudioView = LayoutInflater.from(mContext).inflate(R.layout.item_f33_audio0, null, false);
        mCurrentTv = mAudioView.findViewById(R.id.tv_current_time);
        mTotalTv = mAudioView.findViewById(R.id.tv_total_time);
        mSeekBar = mAudioView.findViewById(R.id.seekbar);
        mImg = mAudioView.findViewById(R.id.img_play_pause);

        mImg.setOnClickListener(v -> {
            AudioPlay.getInstance().playPause(getDatas().get(mShowPosition), null);
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
                AudioPlay.getInstance().playPause(getDatas().get(mShowPosition), seekBar.getProgress());
            }
        });

        AudioPlay.getInstance().setListener(new AudioPlay.Listener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDurationChange(int duration) {
                mHandler.post(() -> {
                    mTotalTv.setText(Integer.toString(duration));
                    mSeekBar.setMax(duration);
                });
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCurrentDurationChange(int duration) {
                mHandler.post(() -> {
                    mCurrentTv.setText(Integer.toString(duration));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mSeekBar.setProgress(duration, true);
                    } else {
                        mSeekBar.setProgress(duration);
                    }
                });
            }

            @Override
            public void onPlayStatusChange(boolean isPlay) {
                mHandler.post(() -> {
                    mImg.setImageResource(isPlay ? R.mipmap.img_audio_pause : R.mipmap.img_audio_play);
                });
            }
        });
    }

    @Override
    protected void convert(ViewHolder holder, String bean, int position) {
        Log.d(TAG, position + "," + holder.hashCode());
        holder.getView(R.id.layout_second).setVisibility(mShowPosition == position ? View.GONE : View.VISIBLE);

        //不该在当前item显示，但显示了，隐藏
        if (null != mAudioView.getParent() &&
                mAudioView.getParent().hashCode() == holder.getView(R.id.layout_root).hashCode() &&
                mShowPosition != position) {
            ((FrameLayout) mAudioView.getParent()).removeView(mAudioView);
        }
        //应该在当前item显示，但没有显示，先移除，再添加
        if (mShowPosition == position) {
            if (mAudioView.getParent() instanceof FrameLayout &&
                    mAudioView.getParent().hashCode() != holder.getView(R.id.layout_root).hashCode()) {
                ((FrameLayout) mAudioView.getParent()).removeView(mAudioView);
            }
            ((FrameLayout) holder.getView(R.id.layout_root)).addView(mAudioView);
        }

        holder.setImageResource(R.id.img_play_pause, R.mipmap.img_audio_play);
        holder.setText(R.id.tv_current_time, "0");
        ((SeekBar) holder.getView(R.id.seekbar)).setProgress(0);
        AudioPlay.getInstance().getAudioTotalDuration(bean, holder.getView(R.id.seekbar), holder.getView(R.id.tv_total_time));

        holder.getView(R.id.img_play_pause).setOnClickListener(v -> {
            setShowPosition(position);
            AudioPlay.getInstance().playPause(bean, null);
        });
        ((SeekBar) holder.getView(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                AudioPlay.getInstance().pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setShowPosition(position);
                AudioPlay.getInstance().playPause(bean, seekBar.getProgress());
            }
        });
    }

    private void setShowPosition(int position) {
        if (mShowPosition != position) {
            mSeekBar.setProgress(0);
            mCurrentTv.setText("0");
            mTotalTv.setText("0");
            mImg.setImageResource(R.mipmap.img_audio_play);
            AudioPlay.getInstance().quit();
        }
        mShowPosition = position;
        notifyDataSetChanged();
    }
}
