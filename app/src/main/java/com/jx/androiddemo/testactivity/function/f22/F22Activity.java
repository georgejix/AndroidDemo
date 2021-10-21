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
    private final int WIDTH = 2160, HEIGHT = 3840;

    @BindView(R.id.img_bitmap)
    ImageView bitmapImg;

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
        yuvPath = BaseApplication.getFile() + File.separator + "yuv2.yuv";
        transform();
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
            //transNv12ToNv21(in);
            //filter(in);
            liangduhalf(in);
            //huibaitu(in);
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
            bitmapImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void liangduhalf(byte in[]){
        //亮度减半
        for (int index = 0; index < WIDTH * HEIGHT; index++) {
            int temp = in[index];
            if (temp > 0) {
                temp /= 2;
            } else {
                temp = (temp + 255) / 2;
            }
            in[index] = (byte) temp;
        }

    }

    private void huibaitu(byte in[]){
        //去除色度，变成灰白图
        for (int index = WIDTH * HEIGHT; index < in.length; index++) {
            in[index] = -128;
        }
    }

    private void filter(byte in[]) {
        //y全0，uv全127->黑色
        //y全0，uv全-128->黑色

        //y全-128，uv全-128->灰色
        //y全127，uv全-128->灰色

        //y全-1，uv全127->白色
        //y全-1，uv全-128->白色
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            in[i] = -127;
        }
        for (int i = WIDTH * HEIGHT; i < in.length; i++) {
            in[i] = -128;
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