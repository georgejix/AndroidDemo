package com.jx.androiddemo.testactivity.function.f21to30.f22;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

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

    @BindView(R.id.tv_quan_bai)
    View tv_quan_bai;
    @BindView(R.id.tv_quan_hei)
    View tv_quan_hei;
    @BindView(R.id.tv_origin_pic)
    View tv_origin_pic;
    @BindView(R.id.tv_half_liang_du)
    View tv_half_liang_du;
    @BindView(R.id.tv_hui_bai)
    View tv_hui_bai;
    @BindView(R.id.tv_tiao_wen)
    View tv_tiao_wen;

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
        yuvPath = BaseApplication.getInstance().getFile() + File.separator + "yuv2.yuv";
        //saveYuvToPic2();
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
        RxView.clicks(tv_quan_bai)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    filterPic(FilterEnum.QUAN_BAI);
                });
        RxView.clicks(tv_quan_hei)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    filterPic(FilterEnum.QUAN_HEI);
                });
        RxView.clicks(tv_origin_pic)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    originPic();
                });
        RxView.clicks(tv_half_liang_du)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    filterPic(FilterEnum.LIANG_DU_HALF);
                });
        RxView.clicks(tv_hui_bai)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    filterPic(FilterEnum.HUI_BAI);
                });
        RxView.clicks(tv_tiao_wen)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    filterPic(FilterEnum.TIAO_WEN);
                });
    }

    private void originPic() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(yuvPath));
            byte in[] = new byte[fileInputStream.available()];
            fileInputStream.read(in);
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
            bitmapImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterPic(FilterEnum filterEnum) {
        switch (filterEnum) {
            case QUAN_BAI:
                try {
                    byte in[] = new byte[WIDTH * HEIGHT * 3 / 2];
                    quanbaiFilter(in);
                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
                    Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
                    bitmapImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case QUAN_HEI:
                try {
                    byte in[] = new byte[WIDTH * HEIGHT * 3 / 2];
                    quanheiFilter(in);
                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
                    Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
                    bitmapImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case LIANG_DU_HALF:
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File(yuvPath));
                    byte in[] = new byte[fileInputStream.available()];
                    fileInputStream.read(in);
                    liangduhalfFilter(in);
                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
                    Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
                    bitmapImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case HUI_BAI:
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File(yuvPath));
                    byte in[] = new byte[fileInputStream.available()];
                    fileInputStream.read(in);
                    huibaituFilter(in);
                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
                    Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
                    bitmapImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TIAO_WEN:
                try {
                    byte in[] = new byte[WIDTH * HEIGHT * 3 / 2];
                    tiaowenFilter(in);
                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT, WIDTH, HEIGHT);
                    Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
                    bitmapImg.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //生成全白/全黑图片
    private void quanbaiFilter(byte in[]) {
        //y全0，uv全127->黑色
        //y全0，uv全-128->黑色

        //y全-128，uv全-128->灰色
        //y全127，uv全-128->灰色

        //y全-1，uv全127->白色
        //y全-1，uv全-128->白色
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            in[i] = -1;
        }
        for (int i = WIDTH * HEIGHT; i < in.length; i++) {
            in[i] = -128;
        }
    }

    private void quanheiFilter(byte in[]) {
        //y全0，uv全127->黑色
        //y全0，uv全-128->黑色

        //y全-128，uv全-128->灰色
        //y全127，uv全-128->灰色

        //y全-1，uv全127->白色
        //y全-1，uv全-128->白色
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            in[i] = 0;
        }
        for (int i = WIDTH * HEIGHT; i < in.length; i++) {
            in[i] = -128;
        }
    }

    //亮度减半
    private void liangduhalfFilter(byte in[]) {
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

    //去除色度，变成灰白图
    private void huibaituFilter(byte in[]) {
        for (int index = WIDTH * HEIGHT; index < in.length; index++) {
            in[index] = -128;
        }
    }

    //生成条纹图片
    private void tiaowenFilter(byte in[]) {
        for (int h = 0; h < HEIGHT; h++) {

            int ty = (255 / 10) * (h * 10 / HEIGHT);
            if (ty > 127) {
                ty -= 256;
            }
            byte y = (byte) ty;
            Log.d(TAG, ty + "");

            for (int w = 0; w < WIDTH; w++) {
                in[h * WIDTH + w] = y;
            }
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
        File file = new File(BaseApplication.getInstance().getFile() + File.separator + "yuvtopic2.jpg");
        try {
            file.createNewFile();
            FileOutputStream filecon = new FileOutputStream(file);
            byte bytes[] = new byte[640 * 480 * 3 / 2];
            YuvImage image = new YuvImage(bytes, ImageFormat.NV21, 640, 480, null);
            image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 80, filecon);
        } catch (Exception e) {
        }
    }

    /**
     * rgb转yuv公式
     * Y= 0.299*R+0.587*G+0.114*B
     * U=-0.147*R-0.289*G+0.463*B
     * V= 0.615*R-0.515*G-0.100*B
     *
     * y = (unsigned char)( ( 66 * r + 129 * g +  25 * b + 128) >> 8) + 16;
     * u = (unsigned char)( ( -38 * r -  74 * g + 112 * b + 128) >> 8) + 128;
     * v = (unsigned char)( ( 112 * r -  94 * g -  18 * b + 128) >> 8) + 128;
     *
     */
}