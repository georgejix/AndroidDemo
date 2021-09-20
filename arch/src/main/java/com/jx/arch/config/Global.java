package com.jx.arch.config;

import com.jx.arch.util.SharePref;

/**
 * <全局静态缓存数据>
 * <功能详细描述>
 *
 * @author wangtao
 */
public class Global
{
    /**
     * 网络切换
     */
    private static final String NET_CHANGED = "NET_CHANGED";

    /**
     * 加密串
     */
    private static final String USER_TOKEN = "USER_TOKEN";

    /**
     * 加密串
     */
    public static String getUserToken()
    {
        return SharePref.getString(USER_TOKEN, "");
    }

    public static void saveUserToken(String userToken)
    {
        SharePref.saveString(USER_TOKEN, userToken);
    }

    /**
     * 网络切换
     */
    public static boolean getNetChanged()
    {
        return SharePref.getBoolean(NET_CHANGED, false);
    }

    public static void saveNetChanged(boolean httpDns)
    {
        SharePref.saveBoolean(NET_CHANGED, httpDns);
    }

    /**
     * 清空 SharePrefSetting 的  SP
     */
    public static void clearAll()
    {
        SharePref.clear();
    }
}
