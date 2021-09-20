package com.jx.arch.net;

import com.jx.arch.config.Config;
import com.jx.arch.util.GeneralUtils;
import com.jx.arch.util.QMLog;
import com.tencent.msdk.dns.MSDKDnsResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类名称：OkHttp3Utils
 * 创建人：wang
 * 类描述：封装一个OkHttp3的获取类
 */
public enum OkHttp3Utils implements BaseOkHttpInterface
{
    BASE_HTTP
            {

                private OkHttpClient mOkHttpClient;

                /**
                 * 获取OkHttpClient对象
                 */
                @Override
                public OkHttpClient getOkHttpClient()
                {
                    if (null == mOkHttpClient)
                    {
                        final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(OkHttp3Config.trustAllCert);
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> QMLog.i(Config.HTTP, "message:" + message));
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        mOkHttpClient = new OkHttpClient.Builder()
                                .cookieJar(new CookiesManager())
                                .sslSocketFactory(sslSocketFactory, OkHttp3Config.trustAllCert)
                                .addInterceptor(interceptor)
                                //添加网络连接器
                                .addNetworkInterceptor(OkHttp3Config.mTokenInterceptor)
                                //设置请求读写的超时时间
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .connectionPool(new ConnectionPool(32, 5, TimeUnit.MINUTES))
                                .cache(OkHttp3Config.cache)
                                .build();
                        mOkHttpClient.dispatcher().setMaxRequestsPerHost(32);
                    }
                    return mOkHttpClient;
                }
            },
    CASH_HTTP
            {

                private OkHttpClient mOkHttpClient;

                /**
                 * 获取OkHttpClient对象
                 */
                @Override
                public OkHttpClient getOkHttpClient()
                {

                    if (null == mOkHttpClient)
                    {
                        final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(OkHttp3Config.trustAllCert);
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> QMLog.i(Config.HTTP, "message:" + message));
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        mOkHttpClient = new OkHttpClient.Builder()
                                .cookieJar(new CookiesManager())
                                .sslSocketFactory(sslSocketFactory, OkHttp3Config.trustAllCert)
                                .addInterceptor(interceptor)
                                //添加网络连接器
                                .addNetworkInterceptor(OkHttp3Config.mTokenInterceptor)
                                //设置请求读写的超时时间
                                .connectTimeout(4, TimeUnit.SECONDS)
                                .writeTimeout(4, TimeUnit.SECONDS)
                                .readTimeout(4, TimeUnit.SECONDS)
                                .connectionPool(new ConnectionPool(16, 5, TimeUnit.MINUTES))
                                .cache(OkHttp3Config.cache)
                                .build();
                    }
                    return mOkHttpClient;
                }
            },
    DNS_HTTP
            {

                private OkHttpClient mOkHttpClient;

                /**
                 * 获取OkHttpClient对象
                 */
                @Override
                public OkHttpClient getOkHttpClient()
                {
                    if (null == mOkHttpClient)
                    {
                        final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(OkHttp3Config.trustAllCert);

                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> QMLog.i(Config.HTTP, "message:" + message));
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        //同样okhttp3后也使用build设计模式
                        mOkHttpClient = new OkHttpClient.Builder()
                                .dns(hostname -> {
                                    if (GeneralUtils.isNullOrZeroLength(OkHttp3Config.ips))
                                    {
                                        OkHttp3Config.ips = MSDKDnsResolver.getInstance().getAddrByName(hostname);
                                    }
                                    String[] ipArr = OkHttp3Config.ips.split(";");
                                    if (0 == ipArr.length)
                                    {
                                        return Collections.emptyList();
                                    }
                                    List<InetAddress> inetAddressList = new ArrayList<>(ipArr.length);
                                    for (String ip : ipArr)
                                    {
                                        if ("0".equals(ip))
                                        {
                                            continue;
                                        }
                                        try
                                        {
                                            InetAddress inetAddress = InetAddress.getByName(ip);
                                            inetAddressList.add(inetAddress);
                                        } catch (UnknownHostException ignored)
                                        {
                                        }
                                    }
                                    return inetAddressList;
                                }).cookieJar(new CookiesManager())
                                .sslSocketFactory(sslSocketFactory, OkHttp3Config.trustAllCert)
                                .addInterceptor(interceptor)
                                //添加网络连接器
                                .addNetworkInterceptor(OkHttp3Config.mTokenInterceptor)
                                //设置请求读写的超时时间
                                .connectTimeout(20, TimeUnit.SECONDS)
                                .writeTimeout(20, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .connectionPool(new ConnectionPool(16, 5, TimeUnit.MINUTES))
                                .cache(OkHttp3Config.cache)
                                .build();
                    }
                    return mOkHttpClient;
                }
            },
    IM_HTTP
            {

                private OkHttpClient mOkHttpClient;

                /**
                 * 获取OkHttpClient对象
                 */
                @Override
                public OkHttpClient getOkHttpClient()
                {
                    if (null == mOkHttpClient)
                    {
                        final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(OkHttp3Config.trustAllCert);
                        mOkHttpClient = new OkHttpClient.Builder()
                                .sslSocketFactory(sslSocketFactory, OkHttp3Config.trustAllCert)
                                .pingInterval(15, TimeUnit.SECONDS)
                                .retryOnConnectionFailure(true)
                                .build();
                    }
                    return mOkHttpClient;
                }
            }
}
