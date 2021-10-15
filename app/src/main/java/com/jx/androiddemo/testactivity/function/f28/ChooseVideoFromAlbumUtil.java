package com.jx.androiddemo.testactivity.function.f28;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseVideoFromAlbumUtil {
    /**
     * 获取所有图片
     *
     * @param context
     * @return
     */
    public static List<VideoFolderInfo> getAllVideoFolder(Context context) {
        final ArrayList<VideoFolderInfo> allVideoFolderList = new ArrayList<>();
        HashMap<Long, VideoFolderInfo> bucketMap = new HashMap<>();
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
                    long bucketId = cursor.getLong(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
                    final int durationColumn = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                    final int sizeColumn = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);

                    final long imageId = cursor.getLong(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    final long duration = cursor.getLong(durationColumn);
                    final long size = cursor.getLong(sizeColumn);
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
