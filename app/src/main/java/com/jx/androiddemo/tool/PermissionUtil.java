package com.jx.androiddemo.tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.jx.androiddemo.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    //获取需要请求的权限
    public static List<String> getNeedRequirePermission() {
        List<String> permissionLists = new ArrayList<>();
        Context context = BaseApplication.getInstance().getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.CAMERA);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.RECORD_AUDIO);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, Manifest.permission.MEDIA_CONTENT_CONTROL) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.MEDIA_CONTENT_CONTROL);
        }
        return permissionLists;
    }
}
