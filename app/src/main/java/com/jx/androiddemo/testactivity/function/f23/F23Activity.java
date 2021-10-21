package com.jx.androiddemo.testactivity.function.f23;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F23Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View,
        Handler.Callback {
    private final static String TAG = "Mp4ToYuvActivity";

    private OnFrameListener onFrameListener;
    private DisplayMetrics dm;
    private boolean initImage = false;
    private NV21ToBitmap nv21ToBitmap;
    private int imgWidth, imgHeight;
    private Handler mHandler;

    private final static int SET_IMG = 1001;
    private final static int PARSE_MP4 = 1002;

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f23;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
        init();
        mHandler.sendEmptyMessage(PARSE_MP4);
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

    private void init() {
        dm = getResources().getDisplayMetrics();
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper(), this);

        onFrameListener = new OnFrameListener() {
            @Override
            public void onFrame(final Image image) {
                if (!initImage) {
                    imgWidth = dm.widthPixels;
                    imgHeight = dm.widthPixels * image.getHeight() / image.getWidth();
                    mHandler.sendEmptyMessage(SET_IMG);
                    nv21ToBitmap = new NV21ToBitmap(mContext,
                            imgWidth, imgHeight, image.getWidth(), image.getHeight());
                    initImage = true;
                }

                byte[] yuv = new byte[image.getWidth() * image.getHeight() * 3 / 2];
                image.getPlanes()[0].getBuffer().get(yuv, 0, image.getWidth() * image.getHeight());
                image.getPlanes()[2].getBuffer().get(yuv, image.getWidth() * image.getHeight(),
                        image.getWidth() * image.getHeight() / 2 - 1);
                /*image.getPlanes()[2].getBuffer().get(yuv, image.getWidth() * image.getHeight() * 5 / 4,
                        image.getWidth() * image.getHeight() / 4);*/
                final Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(yuv);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != img) {
                            img.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        };
    }

    private void TransformMp4ToYuv() {
        String mp4Path = BaseApplication.getFile() + File.separator + "1.mp4";
        Mp4Decoder mp4Decoder = new Mp4Decoder();
        try {
            mp4Decoder.init(mp4Path);
            mp4Decoder.setOnFrameListener(onFrameListener);
            mp4Decoder.videoDecode();
            mp4Decoder.release();
        } catch (IOException e) {
            Log.d(TAG, e + "");
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SET_IMG:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, imgWidth + "," + imgHeight);
                        img.setLayoutParams(new LinearLayout.LayoutParams(imgWidth,
                                imgHeight));
                    }
                });
                break;
            case PARSE_MP4:
                TransformMp4ToYuv();
                break;
        }
        return true;
    }
}