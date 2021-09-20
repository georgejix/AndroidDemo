package com.jx.arch.util;

/**
 * Created by WangTao
 */
public class ErrorCodeManager
{
    private ErrorCodeManager()
    {
    }

    private static class Instance
    {
        static ErrorCodeManager errorCodeManager = new ErrorCodeManager();
    }

    public static ErrorCodeManager getInstance()
    {
        return Instance.errorCodeManager;
    }

    /**
     * 错误码监听
     */
    public OnErrorCodeListener onErrorCodeListener;

    public void setOnErrorCodeListener(OnErrorCodeListener onErrorCodeListener)
    {
        this.onErrorCodeListener = onErrorCodeListener;
    }

    public interface OnErrorCodeListener
    {
        void onErrorCode(String status, String message);
    }

    /**
     * 数据库版本监听
     */
    public OnRealmOldVersionListener onRealmOldVersionListener;

    public void setOnRealmOldVersionListener(OnRealmOldVersionListener onRealmOldVersionListener)
    {
        this.onRealmOldVersionListener = onRealmOldVersionListener;
    }

    public interface OnRealmOldVersionListener
    {
        void onRealmClear(); // 清除数据库
    }

}
