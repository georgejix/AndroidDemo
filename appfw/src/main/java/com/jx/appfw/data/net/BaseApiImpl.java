package com.jx.appfw.data.net;

import com.jx.arch.net.ApiConnection;

import java.net.MalformedURLException;

/**
 * 网络请求处理类封装
 */
public class BaseApiImpl
{
    /**
     * POST请求
     *
     * @param url
     * @param requestJson
     * @return
     * @throws MalformedURLException
     */
    public String requestFromApi(String url, String requestJson) throws MalformedURLException
    {
        return ApiConnection.createPost(url, requestJson).requestPostSyncCall();
    }

    /**
     * GET请求
     *
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public String requestFromApi(String url) throws MalformedURLException
    {
        return ApiConnection.createGET(url).requestGetSyncCall();
    }
}
