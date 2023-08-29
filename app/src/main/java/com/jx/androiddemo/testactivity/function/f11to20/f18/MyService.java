package com.jx.androiddemo.testactivity.function.f11to20.f18;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jix on 2019/2/27.
 */

public class MyService extends Service
{
    private final String TAG = "MyService";

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    //多次调用，只有第一次执行
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind");
        return null;
    }

    //多次调用，多次执行
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
        try
        {
            URL url = new URL("https://www.baidu.com");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(3000);
            connection.connect();
            int code = connection.getResponseCode();
            Log.d(TAG, code+"");
        }catch (Exception e){
            Log.d(TAG, "err" + e);
        }
    }

    //多次调用，多次执行
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }
}
