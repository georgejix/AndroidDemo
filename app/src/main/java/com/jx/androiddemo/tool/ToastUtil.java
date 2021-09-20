package com.jx.androiddemo.tool;

import android.content.Context;
import android.widget.Toast;

import com.jx.arch.util.GeneralUtils;

public class ToastUtil {
    private static Toast toast;

    //显示文本的Toast
    public static void showTextToast(Context context, String message) {
        if (GeneralUtils.isNotNullOrZeroLength(message) && GeneralUtils.isNotNull(context)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
