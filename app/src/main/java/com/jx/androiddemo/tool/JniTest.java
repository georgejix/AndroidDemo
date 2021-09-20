package com.jx.androiddemo.tool;

import android.util.Log;

/**
 * Created by jix on 2019/1/14.
 */

/**
 * jni调用
 * 注意.cpp和.h方法名，要和java里面一致
 */
public class JniTest {
    private static final String TAG = "JniTest";

    static{
        System.loadLibrary("jniTest");
    }

    public native int test();

    public void onProgressCallBack(long i, long m){
        Log.d(TAG, i + "," + m);
    }

    public void myCallback(int a) {
        Log.e(TAG, "Callback: " + a);
    }
}
