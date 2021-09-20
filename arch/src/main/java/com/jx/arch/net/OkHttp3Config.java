package com.jx.arch.net;

import android.os.Build;

import com.jx.arch.config.Config;
import com.jx.arch.config.Global;
import com.jx.arch.util.ArchTool;
import com.jx.arch.util.DeviceUtils;
import com.jx.arch.util.QMLog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.Request;

public class OkHttp3Config
{
    /**
     * 特殊请求的网络白名单
     * eg: 设置短超时时间
     */
    public static final String[] CashWhiteList = new String[]
            {"/seller/create/order/v2"};

    /**
     * 设置缓存目录
     */
    public static final File cacheDirectory = new File(ArchTool.getContext().getCacheDir().getAbsolutePath(), Config.BASE_DIR);

    public static final Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    public static String ips = "";

    /**
     * 那个 if 判断意思是，如果你的 token 是空的，就是还没有请求到 token，
     * 比如对于登陆请求，是没有 token 的，只有等到登陆之后才有 token，
     * 这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
     * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
     * header 的 key 通常是 Authorization，如果你的不是这个，可以修改。
     */
    public static Interceptor mTokenInterceptor = chain ->
    {
        Request originalRequest = chain.request();
        QMLog.i("token", "token: " + Global.getUserToken());
        Request authorised = originalRequest.newBuilder()
                .header("Authorization", Global.getUserToken())
                .header("sn", DeviceUtils.getDeviceSN())
                .header("vsname", Config.VERSION_NAME)
                .header("model", getValueEncoded(Build.MODEL))
                .build();
        return chain.proceed(authorised);
    };

    // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
    public static final X509TrustManager trustAllCert =
            new X509TrustManager()
            {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
                {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
                {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return new java.security.cert.X509Certificate[]{};
                }
            };

    //由于okhttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
    //会首先替换 \n ,然后使用 okhttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
    private static String getValueEncoded(String value)
    {
        if (value == null)
        {
            return "null";
        }
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++)
        {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f')
            {
                try
                {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }
}
