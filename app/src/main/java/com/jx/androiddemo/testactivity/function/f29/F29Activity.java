package com.jx.androiddemo.testactivity.function.f29;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.arch.util.QMLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F29Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.tv_download)
    TextView tv_download;
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
    private Handler mBackHandler;

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
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mBackHandler = new Handler(handlerThread.getLooper());
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
        RxView.clicks(tv_download)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    String url = "https://vd2.bdstatic.com/mda-mjbzwb7wzwnuvqhj/sc/cae_h264/1634083355852877186/mda-mjbzwb7wzwnuvqhj.mp4";
                    //String url = "https://v26-web.douyinvod.com/29195f4d4c577bf0a8e24b08bc0143ef/61665946/video/tos/cn/tos-cn-ve-15/1ce4d80e5d3743f08e2161583a43e104/?a=6383&br=3312&bt=3312&cd=0%7C0%7C0&ch=26&cr=0&cs=0&cv=1&dr=0&ds=4&er=&ft=jal9wj--bz7ThW_TfLct&l=021634093859543fdbddc0200fff0050a9138570000c0f760b4ec&lr=all&mime_type=video_mp4&net=0&pl=0&qs=0&rc=M3V2MzU6ZjVxODMzNGkzM0ApODM4Mzw5OmU5Nzg8OzdkNWc2Xm0wcjRnZmRgLS1kLTBzczQwLy4zNWMwNTM0M2NgL146Yw%3D%3D&vl=&vr=";
                    String localPath = BaseApplication.getFile() + File.separator + "temp.mp4";
                    mBackHandler.post(() -> {
                        download(url, localPath);
                        mSourcePath = localPath;
                        mAudioPath = BaseApplication.getFile() + File.separator + "temp.mp3";
                        filterAudio();
                    });
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

    private void download(String remotePath, String path) {
        // 下载具体操作
        try {
            URL url = new URL(remotePath);
            // 打开连接
            URLConnection conn = url.openConnection();
            // 打开输入流
            InputStream is = conn.getInputStream();
            Log.e(TAG, "length:" + conn.getContentLength());
            // 创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(path);
            // 写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完成后关闭流
            Log.e(TAG, "download-finish");
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e.getMessage() --- " + e.getMessage());
        }
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