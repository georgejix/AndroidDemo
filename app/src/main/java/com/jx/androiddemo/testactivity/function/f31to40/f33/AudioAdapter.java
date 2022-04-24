package com.jx.androiddemo.testactivity.function.f31to40.f33;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.SeekBar;

import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.f31to40.f32.AudioPlay;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class AudioAdapter extends CommonAdapter<String> {

    private ViewHolder mViewHolder;

    @Inject
    public AudioAdapter(Context context) {
        super(context, R.layout.item_f33_audio);
    }

    public void init(Handler handler) {
        AudioPlay.getInstance().setListener(new AudioPlay.Listener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDurationChange(int duration) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCurrentDurationChange(int duration) {
                handler.post(() -> {
                    mViewHolder.setText(R.id.tv_current_time, Integer.toString(duration));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        ((SeekBar) mViewHolder.getView(R.id.seekbar)).setProgress(duration, true);
                    } else {
                        ((SeekBar) mViewHolder.getView(R.id.seekbar)).setProgress(duration);
                    }
                });
            }

            @Override
            public void onPlayStatusChange(boolean isPlay) {
                handler.post(() -> {
                    mViewHolder.setImageResource(R.id.img_play_pause, isPlay ? R.mipmap.img_audio_pause : R.mipmap.img_audio_play);
                });
            }
        });
    }

    @Override
    protected void convert(ViewHolder holder, String bean, int position) {
        holder.setImageResource(R.id.img_play_pause, R.mipmap.img_audio_play);
        holder.setText(R.id.tv_current_time, "0");

        if (null == mViewHolder && 0 == position) {
            mViewHolder = holder;
        }
        AudioPlay.getInstance().getAudioTotalDuration(bean, holder.getView(R.id.seekbar), holder.getView(R.id.tv_total_time));
        holder.getView(R.id.img_play_pause).setOnClickListener(v -> {
            setViewHolder(holder);
            AudioPlay.getInstance().playPause(bean, null);
        });
        ((SeekBar) holder.getView(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setViewHolder(holder);
                AudioPlay.getInstance().pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AudioPlay.getInstance().playPause(bean, seekBar.getProgress());
            }
        });
    }

    private void setViewHolder(ViewHolder holder) {
        if (mViewHolder != holder) {
            mViewHolder.setImageResource(R.id.img_play_pause, R.mipmap.img_audio_play);
            mViewHolder.setText(R.id.tv_current_time, Integer.toString(0));
        }
        mViewHolder = holder;
    }
}
