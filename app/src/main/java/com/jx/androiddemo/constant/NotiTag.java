package com.jx.androiddemo.constant;

import android.app.Activity;
import android.content.Context;

import com.jx.androiddemo.BaseApplication;

public class NotiTag
{
    /**
     * 网络链接错误码
     */
    public static final String TAG_ERROR_CODE = "TAG_ERROR_CODE";

    public static final String TAG_TITLE_LEFT = "TAG_TITLE_LEFT";

    /**
     * 网络链接错误信息
     */
    public static final String TAG_ERROR_INFO = "TAG_ERROR_INFO";

    public static final String TAG_HIDDEN_BACKGROUND = "TAG_HIDDEN_BACKGROUND";

    public static final String TAG_SHOW_FONT = "TAG_SHOW_FONT";

    /**
     * 在当前界面操作tag
     */
    public static boolean equalsTags(Context context, String tagOne, String tagTwo)
    {
        Activity activity = BaseApplication.getInstance().getCurrentActivity();
        if (null == activity)
        {
            return false;
        }
        return tagOne.equals(tagTwo) && activity.getClass().getName().equals(context.getClass().getName());
    }
}
