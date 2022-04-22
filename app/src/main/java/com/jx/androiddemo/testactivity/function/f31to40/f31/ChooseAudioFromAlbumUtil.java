package com.jx.androiddemo.testactivity.function.f31to40.f31;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseAudioFromAlbumUtil {
    public static List<AudioFolderInfo> getAllAudioFolder(Context context) {
        //文件夹数组
        final ArrayList<AudioFolderInfo> allAudioFolderList = new ArrayList<>();
        //文件夹map
        HashMap<Long, AudioFolderInfo> bucketMap = new HashMap<>();

        Cursor cursor = null;
        //所有图片
        AudioFolderInfo allAudioFolderInfo = new AudioFolderInfo();
        allAudioFolderInfo.setFolderId(0);
        allAudioFolderInfo.setFolderName("all audio");
        allAudioFolderInfo.setAudioList(new ArrayList<>());
        allAudioFolderList.add(0, allAudioFolderInfo);

        try {
            cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                final int dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                final int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                final int sizeColumn = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);

                while (cursor.moveToNext()) {
                    //文件夹id
                    long bucketId = cursor.getLong(bucketIdColumn);
                    //文件夹名称
                    String bucketName = cursor.getString(bucketNameColumn);
                    //音频id
                    final long id = cursor.getLong(idColumn);
                    //音频地址
                    final String path = cursor.getString(dataColumn);
                    //音频时长
                    long duration = cursor.getLong(durationColumn);
                    //音频文件大小
                    final long size = cursor.getLong(sizeColumn);

                    File file = new File(path);
                    if (file.exists() && file.length() > 0) {
                        final AudioInfo audioInfo = new AudioInfo();
                        audioInfo.setAudioId(id);
                        audioInfo.setAudioPath(path);
                        audioInfo.setDuration(duration);
                        audioInfo.setSize(size);
                        if (allAudioFolderInfo.getCoverPhoto() == null) {
                            allAudioFolderInfo.setCoverPhoto(audioInfo);
                        }
                        //添加到所有音频
                        allAudioFolderInfo.getAudioList().add(audioInfo);

                        //通过bucketId获取文件夹
                        AudioFolderInfo audioFolderInfo = bucketMap.get(bucketId);

                        if (audioFolderInfo == null) {
                            audioFolderInfo = new AudioFolderInfo();
                            audioFolderInfo.setAudioList(new ArrayList<>());
                            audioFolderInfo.setFolderId(bucketId);
                            audioFolderInfo.setFolderName(bucketName);
                            audioFolderInfo.setCoverPhoto(audioInfo);
                            bucketMap.put(bucketId, audioFolderInfo);
                            allAudioFolderList.add(audioFolderInfo);
                        }
                        audioFolderInfo.getAudioList().add(audioInfo);

                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allAudioFolderList;
    }
}
