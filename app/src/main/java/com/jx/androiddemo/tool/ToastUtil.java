package com.jx.androiddemo.tool;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.jx.androiddemo.BaseApplication;

public class ToastUtil {
    private static Toast mToast;
    private static long mLastThreadId;

    //显示文本的Toast
    @SuppressLint("ShowToast")
    public static void showTextToast(String message) {
        if (null == mToast || Thread.currentThread().getId() != mLastThreadId) {
            mToast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
            mLastThreadId = Thread.currentThread().getId();
        }
        mToast.show();
    }
}
