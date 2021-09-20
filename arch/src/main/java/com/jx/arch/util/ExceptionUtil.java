package com.jx.arch.util;


import com.jx.arch.BuildConfig;

/**
 * 上传exception到友盟
 */
public class ExceptionUtil
{
    private final static String TAG = "ExceptionUtil";

    //realm异常上报
    public static void uploadRealmException(Exception exception)
    {
        QMLog.i(TAG, getErrorMsg(exception));
        if (null == exception || BuildConfig.ON_OFF || null == Thread.currentThread().getStackTrace()
                || Thread.currentThread().getStackTrace().length <= 3)
        {
            return;
        }

        if (BuildConfig.ON_OFF)
        {
            exception.printStackTrace();
        }

        String sn = GeneralUtils.getFilterText(DeviceUtils.getDeviceSN());
        String clazz = Thread.currentThread().getStackTrace()[3].getClassName();
        if (clazz.contains("."))
        {
            clazz = clazz.substring(clazz.lastIndexOf(".") + 1);
        }
        String method = Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    public static String getErrorMsg(Exception e)
    {
        return GeneralUtils.isNotNull(e) && GeneralUtils.isNotNullOrZeroLength(e.getMessage()) ? e.getMessage() : "";
    }
}
