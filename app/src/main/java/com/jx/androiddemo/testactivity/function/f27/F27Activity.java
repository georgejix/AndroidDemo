package com.jx.androiddemo.testactivity.function.f27;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F27Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_choose_img)
    TextView tv_choose_img;
    @BindView(R.id.tv_choose_video)
    TextView tv_choose_video;
    @BindView(R.id.tv_choose_content)
    TextView tv_choose_content;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f27;
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
        RxView.clicks(tv_choose_img)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    List<PhotoFolderInfo> list = getAllPhotoFolder(this);
                    StringBuilder sb = new StringBuilder("");
                    list.remove(0);
                    for (PhotoFolderInfo photoFolderInfo : list) {
                        sb.append(photoFolderInfo.getFolderName()).append(":").append("\n");
                        for (PhotoInfo photoInfo : photoFolderInfo.getPhotoList()) {
                            sb.append(photoInfo.getPhotoPath()).append("\n").append("\n");
                        }
                    }
                    tv_choose_content.setText(sb.toString());
                });
        RxView.clicks(tv_choose_video)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    List<VideoFolderInfo> list = getAllVideoFolder(this);
                    StringBuilder sb = new StringBuilder("");
                    list.remove(0);
                    for (VideoFolderInfo videoFolderInfo : list) {
                        sb.append(videoFolderInfo.getFolderName()).append(":").append("\n");
                        for (VideoInfo videoInfo : videoFolderInfo.getVideoList()) {
                            sb.append(videoInfo.getVideoPath()).append("\n")
                                    .append("duration:").append(videoInfo.getDuration())
                                    .append(",size:").append(videoInfo.getSize())
                                    .append("\n").append("\n");
                        }
                    }
                    tv_choose_content.setText(sb.toString());
                });
    }

    /**
     * 获取所有图片
     *
     * @param context
     * @return
     */
    public static List<PhotoFolderInfo> getAllPhotoFolder(Context context) {
        final String[] projectionPhotos = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Thumbnails.DATA
        };
        final ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
        HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();
        Cursor cursor = null;
        //所有图片
        PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
        allPhotoFolderInfo.setFolderId(0);
        allPhotoFolderInfo.setFolderName("all pic");
        allPhotoFolderInfo.setPhotoList(new ArrayList<>());
        allPhotoFolderList.add(0, allPhotoFolderInfo);
        try {
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                while (cursor.moveToNext()) {
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    //int thumbImageColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    //final String thumb = cursor.getString(thumbImageColumn);
                    File file = new File(path);
                    if (file.exists() && file.length() > 0) {
                        final PhotoInfo photoInfo = new PhotoInfo();
                        photoInfo.setPhotoId(imageId);
                        photoInfo.setPhotoPath(path);
                        //photoInfo.setThumbPath(thumb);
                        if (allPhotoFolderInfo.getCoverPhoto() == null) {
                            allPhotoFolderInfo.setCoverPhoto(photoInfo);
                        }
                        //添加到所有图片
                        allPhotoFolderInfo.getPhotoList().add(photoInfo);

                        //通过bucketId获取文件夹
                        PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);

                        if (photoFolderInfo == null) {
                            photoFolderInfo = new PhotoFolderInfo();
                            photoFolderInfo.setPhotoList(new ArrayList<>());
                            photoFolderInfo.setFolderId(bucketId);
                            photoFolderInfo.setFolderName(bucketName);
                            photoFolderInfo.setCoverPhoto(photoInfo);
                            bucketMap.put(bucketId, photoFolderInfo);
                            allPhotoFolderList.add(photoFolderInfo);
                        }
                        photoFolderInfo.getPhotoList().add(photoInfo);

                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allPhotoFolderList;
    }

    /**
     * 获取所有图片
     *
     * @param context
     * @return
     */
    public static List<VideoFolderInfo> getAllVideoFolder(Context context) {
        final String[] projectionPhotos = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.ORIENTATION,
                MediaStore.Video.Thumbnails.DATA
        };
        final ArrayList<VideoFolderInfo> allVideoFolderList = new ArrayList<>();
        HashMap<Integer, VideoFolderInfo> bucketMap = new HashMap<>();
        Cursor cursor = null;
        //所有图片
        VideoFolderInfo allVideoFolderInfo = new VideoFolderInfo();
        allVideoFolderInfo.setFolderId(0);
        allVideoFolderInfo.setFolderName("all video");
        allVideoFolderInfo.setVideoList(new ArrayList<>());
        allVideoFolderList.add(0, allVideoFolderInfo);
        try {
            cursor = MediaStore.Video.query(context.getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    , null);
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID);
                while (cursor.moveToNext()) {
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
                    final int durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                    final int sizeColumn = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);

                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    final long duration = cursor.getLong(durationColumn);
                    final long size = cursor.getLong(sizeColumn);
                    for (String name : cursor.getColumnNames()) {
                        Log.d("F27", name + ":" + cursor.getString(cursor.getColumnIndex(name)));
                    }
                    File file = new File(path);
                    if (file.exists() && file.length() > 0) {
                        final VideoInfo videoInfo = new VideoInfo();
                        videoInfo.setVideoId(imageId);
                        videoInfo.setVideoPath(path);
                        videoInfo.setDuration(duration);
                        videoInfo.setSize(size);
                        if (allVideoFolderInfo.getCoverPhoto() == null) {
                            allVideoFolderInfo.setCoverPhoto(videoInfo);
                        }
                        //添加到所有图片
                        allVideoFolderInfo.getVideoList().add(videoInfo);

                        //通过bucketId获取文件夹
                        VideoFolderInfo videoFolderInfo = bucketMap.get(bucketId);

                        if (videoFolderInfo == null) {
                            videoFolderInfo = new VideoFolderInfo();
                            videoFolderInfo.setVideoList(new ArrayList<>());
                            videoFolderInfo.setFolderId(bucketId);
                            videoFolderInfo.setFolderName(bucketName);
                            videoFolderInfo.setCoverPhoto(videoInfo);
                            bucketMap.put(bucketId, videoFolderInfo);
                            allVideoFolderList.add(videoFolderInfo);
                        }
                        videoFolderInfo.getVideoList().add(videoInfo);

                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allVideoFolderList;
    }
}