package com.jx.arch.net;

import androidx.annotation.Nullable;

import com.jx.arch.config.Global;
import com.jx.arch.util.GeneralUtils;
import com.jx.arch.util.QMLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.Callable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link Callable} so when executed asynchronously can
 * return a value.
 */
public class ApiConnection
{

    private static final String CONTENT_TYPE_LABEL = "Content-Type";

    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    private static final String OF_CAPTCHA_CONTROL = "OFCaptchaControl";

    private static final MediaType JSON = MediaType.get(CONTENT_TYPE_VALUE_JSON);

    private final URL url;

    private String requestJson;

    public static String pictureCodeHeader = "";

    private String mFilePath;

    private ApiConnection(String url) throws MalformedURLException
    {
        this.url = new URL(url);
    }

    private ApiConnection(String url, String requestJson) throws MalformedURLException
    {
        this.url = new URL(url);
        this.requestJson = requestJson;
    }

    public static ApiConnection createPostFile(String url, String filePath) throws MalformedURLException
    {
        ApiConnection apiConnection = new ApiConnection(url);
        apiConnection.mFilePath = filePath;
        return apiConnection;
    }

    public static ApiConnection createGET(String url) throws MalformedURLException
    {
        return new ApiConnection(url);
    }

    public static ApiConnection createPost(String url, String requestJson) throws MalformedURLException
    {
        return new ApiConnection(url, requestJson);
    }

    @Nullable
    public String requestFileSyncCall()
    {
        return connectToPostFileApi();
    }

    /**
     * Do a request to an api synchronously.
     * It should not be executed in the main thread of the application.
     *
     * @return A string response
     */
    @Nullable
    public String requestGetSyncCall()
    {

        return connectToGetApi();
    }

    @Nullable
    public String requestPostSyncCall()
    {
        return connectToPostApi();
    }

    @Nullable
    public InputStream requestDownLoadSyncCall()
    {
        return connectToDownLoadApi();
    }

    private String connectToGetApi()
    {
        Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .addHeader(OF_CAPTCHA_CONTROL, GeneralUtils.isNotNullOrZeroLength(pictureCodeHeader) ? pictureCodeHeader : "")
                .get()
                .build();
        return doRequestHttpResult(request);
    }

    private String connectToPostApi()
    {
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .addHeader(OF_CAPTCHA_CONTROL, GeneralUtils.isNotNullOrZeroLength(pictureCodeHeader) ? pictureCodeHeader : "")
                .post(body)
                .build();
        return doRequestHttpResult(request);
    }

    private InputStream connectToDownLoadApi()
    {
        OkHttpClient okHttpClient = this.createClient();
        QMLog.i("ApiConnection", "http down: " + okHttpClient.hashCode());
        final Request request = new Request.Builder()
                .url(this.url)
                .build();
        try
        {
            Response mResponse = okHttpClient.newCall(request).execute();
            QMLog.i("pictureCodeHeader", "pictureCodeHeader: " + pictureCodeHeader);
            return mResponse.body().byteStream();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private String connectToPostFileApi()
    {
        OkHttpClient okHttpClient = this.createClient();
        File file = new File(mFilePath);//文件路径
        long size = file.length();//文件长度
        MediaType mediaType = MediaType.parse("image/jpg");//设置类型，类型为八位字节流
        RequestBody requestBody = RequestBody.create(mediaType, file);//把文件与类型放入请求体

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), requestBody)//文件名,请求体里的文件
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, "multipart/form-data")
                .post(multipartBody)
                .build();
        try
        {
            Response mResponse = okHttpClient.newCall(request).execute();
            return mResponse.body().string();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private String doRequestHttpResult(final Request request)
    {
        OkHttpClient okHttpClient = this.createClient();
        QMLog.i("ApiConnection", "http post: " + okHttpClient.hashCode());
        Response mResponse = null;
        String result = null;
        try
        {
            mResponse = okHttpClient.newCall(request).execute();
            if (mResponse.code() == 200)
            {
                result = mResponse.body().string();
                String ofCaptchaControl = mResponse.header(OF_CAPTCHA_CONTROL);
                if (GeneralUtils.isNotNullOrZeroLength(ofCaptchaControl))
                {
                    pictureCodeHeader = mResponse.header(OF_CAPTCHA_CONTROL);
                    QMLog.i("pictureCodeHeader", "pictureCodeHeader: " + pictureCodeHeader);
                }
            }
            return result;
        } catch (SocketTimeoutException | UnknownHostException e)
        {
            e.printStackTrace();
            try
            {
                okHttpClient.connectionPool().evictAll();
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (mResponse != null)
            {
                try
                {
                    mResponse.close();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private OkHttpClient createClient()
    {
        QMLog.i("ApiConnection", "url: " + this.url.getPath() + " --- " + this.url.getHost());
        if (Global.getNetChanged())
        {
            return OkHttp3Utils.DNS_HTTP.getOkHttpClient();
        }
        if (Arrays.asList(OkHttp3Config.CashWhiteList).contains(this.url.getPath()))
        {
            return OkHttp3Utils.CASH_HTTP.getOkHttpClient();
        }
        return OkHttp3Utils.BASE_HTTP.getOkHttpClient();
    }
}
