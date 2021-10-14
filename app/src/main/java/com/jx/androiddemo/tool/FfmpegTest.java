package com.jx.androiddemo.tool;

import android.util.Log;

public class FfmpegTest {
    private static final String TAG = "FfmpegTest";

    static{
        System.loadLibrary("ffmpegTest");

        System.loadLibrary("avcodec");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("postproc");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");
    }

    public native int test(String input_path,String output_path);

    public void onProgressCallBack(long i, long m){
        Log.d(TAG, i + "," + m);
    }

    public void myCallback(int a) {
        Log.e(TAG, "Callback: " + a);
    }
}
