package com.jx.androiddemo.tool;

public class ClickListenerUtil {
    public static final int LIMIT_TIME = 300;

    private static long lastClickTime = 0;

    public static boolean canClick() {
        long curTime = System.currentTimeMillis();

        if (curTime - lastClickTime > LIMIT_TIME) {
            lastClickTime = curTime;
            return true;
        }
        return false;
    }

    public static boolean canClick(int interval) {
        long curTime = System.currentTimeMillis();

        if (curTime - lastClickTime > (interval > 0 ? interval : LIMIT_TIME)) {
            lastClickTime = curTime;
            return true;
        }
        return false;
    }
}
