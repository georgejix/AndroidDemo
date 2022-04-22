package com.jx.androiddemo.testactivity.function.f11to20.f11;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/12/21.
 */

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extras = intent.getExtras();
        MyWakefulReceiver.completeWakefulIntent(intent);
    }
}
