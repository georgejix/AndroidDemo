package com.jx.androiddemo.testactivity.function.f29;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.arch.util.QMLog;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F29Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_filter_mp4)
    View tv_filter_mp4;
    @BindView(R.id.tv_filter_mov)
    View tv_filter_mov;
    @BindView(R.id.tv_filter_flv)
    View tv_filter_flv;
    @BindView(R.id.tv_filter_avi)
    View tv_filter_avi;

    public String mSourcePath;
    public String mAudioPath;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f29;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
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
        RxView.clicks(tv_filter_mp4)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    mSourcePath = BaseApplication.getFile() + File.separator + "2_mp4.mp4";
                    mAudioPath = BaseApplication.getFile() + File.separator + "2_mp4.mp3";
                    filterAudio();
                });
        RxView.clicks(tv_filter_mov)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    mSourcePath = BaseApplication.getFile() + File.separator + "2_mov.mov";
                    mAudioPath = BaseApplication.getFile() + File.separator + "2_mov.mp3";
                    filterAudio();
                });
        RxView.clicks(tv_filter_flv)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    mSourcePath = BaseApplication.getFile() + File.separator + "2_flv.flv";
                    mAudioPath = BaseApplication.getFile() + File.separator + "2_flv.mp3";
                    filterAudio();
                });
        RxView.clicks(tv_filter_avi)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    mSourcePath = BaseApplication.getFile() + File.separator + "2_avi.avi";
                    mAudioPath = BaseApplication.getFile() + File.separator + "2_avi.mp3";
                    filterAudio();
                });
    }

    //过滤音频
    private void filterAudio() {
        MediaExtractor extractor = new MediaExtractor();
        int audioTrack = -1;
        boolean hasAudio = false;
        MediaMuxer mediaMuxer = null;
        try {
            extractor.setDataSource(mSourcePath);
            for (int i = 0; i < extractor.getTrackCount(); i++) {
                MediaFormat trackFormat = extractor.getTrackFormat(i);
                String mime = trackFormat.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio/")) {
                    audioTrack = i;
                    hasAudio = true;
                    break;
                }
            }
            if (hasAudio) {
                extractor.selectTrack(audioTrack);
                mediaMuxer = new MediaMuxer(mAudioPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                MediaFormat trackFormat = extractor.getTrackFormat(audioTrack);
                int writeAudioIndex = mediaMuxer.addTrack(trackFormat);
                mediaMuxer.start();
                ByteBuffer byteBuffer = ByteBuffer.allocate(trackFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE));
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                extractor.readSampleData(byteBuffer, 0);
                if (extractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    extractor.advance();
                }
                while (true) {
                    int readSampleSize = extractor.readSampleData(byteBuffer, 0);
                    Log.d(TAG, "---读取音频数据，当前读取到的大小-----：：：" + readSampleSize);
                    if (readSampleSize < 0) {
                        break;
                    }

                    bufferInfo.size = readSampleSize;
                    bufferInfo.flags = extractor.getSampleFlags();
                    bufferInfo.offset = 0;
                    bufferInfo.presentationTimeUs = extractor.getSampleTime();
                    Log.d(TAG, "----写入音频数据---当前的时间戳：：：" + extractor.getSampleTime());

                    mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
                    extractor.advance();//移动到下一帧
                }
            }
        } catch (Exception e) {
            QMLog.e(TAG, e.getMessage());
        } finally {
            if (null != mediaMuxer) {
                mediaMuxer.release();
            }
            extractor.release();
        }
    }
}