package com.jx.androiddemo.tool;

import android.widget.Toast;

import com.jx.androiddemo.BaseApplication;

public class ToastUtil {
    private static Toast toast;
    private static boolean mNeedCancel = true;
    private static long mLastThreadId;

    public static void showTextToast(String msg) {
        if (toast == null || mLastThreadId != Thread.currentThread().getId()) {
            toast = new Toast(BaseApplication.getInstance().getApplicationContext());
            try {
                toast.setText("");
            } catch (Exception e) {
                mNeedCancel = false;
                toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "", Toast.LENGTH_SHORT);
            }
            mLastThreadId = Thread.currentThread().getId();
        }
        if (mNeedCancel) {
            toast.cancel();
        }
        toast.setText(msg);
        toast.show();
    }
}
