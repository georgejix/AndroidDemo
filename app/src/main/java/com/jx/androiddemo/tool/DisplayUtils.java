package com.jx.androiddemo.tool;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

public class DisplayUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenWidth2(Context context) {
        if (null == context) {
            return 0;
        }
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight2(Context context) {
        if (null == context) {
            return 0;
        }
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getStatusBarHeight(Context context) {
        if (null == context) {
            return 0;
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 是否竖屏
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT == mConfiguration.getLayoutDirection();
    }

    public static boolean isPortrait(WindowManager windowManager) {
        int rotation = 0;
        if (null != windowManager && null != windowManager.getDefaultDisplay()) {
            rotation = windowManager.getDefaultDisplay().getRotation();
        }
        return Surface.ROTATION_0 == rotation || Surface.ROTATION_180 == rotation;
    }
}
