package com.jx.androiddemo.tool;

import android.util.Log;

import com.jx.androiddemo.testactivity.function.f30.PcmToWav;

public class FfmpegTest {
    private static final String TAG = "FfmpegTest";

    static {
        System.loadLibrary("ffmpegTest");

        System.loadLibrary("avcodec");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("postproc");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");
    }

    public native int test(String input_path, String output_path);

    public native int test2(String input_path, String output_path);

    public void onProgressCallBack(long total, long current, String name) {
        Log.d(TAG, String.format("total=%d,current=%d,name=%s", total, current, name));
    }

    public void complete(int ret, String name) {
        Log.e(TAG, "complete: " + ret + name);
        if (null != name) {
            PcmToWav.makePCMFileToWAVFile(name, name.substring(0, name.length() - 4) + ".wav", false);
        }
    }
}
