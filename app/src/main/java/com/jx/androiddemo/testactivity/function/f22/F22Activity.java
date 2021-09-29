package com.jx.androiddemo.testactivity.function.f22;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.widget.ImageView;

import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F22Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final static String TAG = "YuvToBitmapActivity";

    private String yuvPath = "";
    private final int WIDTH = 1280, HEIGHT = 720;

    @BindView(R.id.img_bitmap)
    ImageView bitmapImg;

    static {
        System.loadLibrary("opencv_java");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f22;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        yuvPath = BaseApplication.getFile() + File.separator + "yuv1.yuv";
        transform();
        saveYuvToPic();
        saveYuvToPic2();
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

    private void transform() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(yuvPath));
            byte in[] = new byte[fileInputStream.available()];
            fileInputStream.read(in);
            transNv12ToNv21(in);
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
            bitmapImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transNv12ToNv21(byte in[]) {
        if (null == in || in.length <= WIDTH * HEIGHT)
            return;
        byte temp;
        for (int index = WIDTH * HEIGHT; index + 1 < in.length; index += 2) {
            temp = in[index];
            in[index] = in[index + 1];
            in[index + 1] = temp;
        }
    }


    private void saveYuvToPic() {
        Mat mat = new Mat(480, 640, CvType.CV_8UC1);
        byte bytes[] = new byte[480 * 640];
        mat.put(0, 0, bytes);
        Highgui.imwrite(BaseApplication.getFile() + File.separator + "yuvtopic.jpg", mat);
    }

    private void saveYuvToPic2() {
        File file = new File(BaseApplication.getFile() + File.separator + "yuvtopic2.jpg");
        try {
            file.createNewFile();
            FileOutputStream filecon = new FileOutputStream(file);
            byte bytes[] = new byte[640 * 480 * 3 / 2];
            YuvImage image = new YuvImage(bytes, ImageFormat.NV21, 640, 480, null);
            image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 80, filecon);
        } catch (Exception e) {
        }
    }
}