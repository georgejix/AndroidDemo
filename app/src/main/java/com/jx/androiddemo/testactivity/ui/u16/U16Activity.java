package com.jx.androiddemo.testactivity.ui.u16;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U16Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "CutPicActivity";

    @BindView(R.id.img_1)
    ImageView img1;

    private File cameraFile;

    private final int CHOOSE_FROM_ALBUM = 1001;
    private final int TAKE_PICTURE = 1002;
    private final int REQUEST_CROP_PHOTO = 1003;
    private Uri mPhotoUri;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u16;
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
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    public void chooseFromAlbum(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);// 打开图库获取图片
        intent.setAction(Intent.ACTION_PICK);// 打开图库获取图片
        intent.setType("image/*");// 这个参数是确定要选择的内容为图片
        intent.putExtra("return-data", true);// 是否要返回，如果设置false取到的值就是空值
        startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    public void choosefromcamera(View view) {
        cameraFile = new File(BaseApplication.getFile(), "abc.jpg");
        if (cameraFile.exists()) {
            cameraFile.delete();
        }
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues contentValues = new ContentValues(2);
        //contentValues.put(MediaStore.Images.Media.DATA, cameraFile.getAbsolutePath());
        //如果想拍完存在系统相机的默认目录,改为
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "111111.jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        startActivityForResult(intent2, TAKE_PICTURE);
    }

    private Bitmap bitmap;

    private void afterAlbum(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        try {
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            img1.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_FROM_ALBUM:
                if (Activity.RESULT_OK == resultCode) {
                    afterAlbum(data.getData());
                    //cropPhoto(data.getData());
                }
                break;
            case TAKE_PICTURE:
                if (Activity.RESULT_OK == resultCode) {
                    if (null != mPhotoUri) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(U16Activity.this.getContentResolver()
                                    , mPhotoUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (null != bitmap) {
                            img1.setImageBitmap(bitmap);
                        }
                        //cropPhoto(getUri(cameraFile));
                    }
                }
                break;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri 需要 裁剪图像的Uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (null != uri) {
            //7.0 安全机制下不允许保存裁剪后的图片
            // 所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
            //outUri

            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CROP_PHOTO);
        }
    }

    private Uri getUri(File f) {
        Uri uri = null;
        if (null != f) {
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, getPackageName()
                        + ".provider", f);
            } else {
                uri = Uri.fromFile(f);
            }
        }
        //grantUriPermission(com.mplanet.testhandler.BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return uri;
    }

    /**
     * 以时间戳命名将bitmap写入文件
     *
     * @param bitmap
     */
    public void writeFileByBitmap2(Bitmap bitmap) {
        String path = BaseApplication.getFile();//手机设置的存储位置
        File file = new File(path);
        File imageFile = new File(file, System.currentTimeMillis() + ".png");


        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            imageFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}