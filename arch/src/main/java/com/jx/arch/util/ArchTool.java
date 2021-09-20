package com.jx.arch.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.jx.arch.BuildConfig;
import com.jx.arch.config.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;

public class ArchTool
{
    private static final String TAG = "ArchTool";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context)
    {
        ArchTool.context = context.getApplicationContext();
        Config.VERSION_NAME = DeviceUtils.getVersionName(context);
        Realm.init(context);
        RxJavaPlugins.setErrorHandler(throwable -> {
            QMLog.i(TAG, "throw error");
            if (throwable != null && BuildConfig.ON_OFF)
            {
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 在某种获取不到 Context 的情况下，即可以使用才方法获取 Context
     * <p>
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext()
    {
        if (context != null)
        {
            return context;
        }
        throw new NullPointerException("请先调用init()方法");
    }

    public static String MD5(String string)
    {
        if (string == null || string.isEmpty())
        {
            return "";
        }
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes)
            {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1)
                {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
