package com.jx.androiddemo.testactivity.function.f11to20.f11;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/12/21.
 */

public class MyIntentService extends IntentService {
    private final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extras = intent.getExtras();
        MyWakefulReceiver.completeWakefulIntent(intent);
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
}
