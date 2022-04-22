package com.jx.androiddemo.testactivity.ui.ui21to30.ui27;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LocalHandler extends Handler {
    private final Listener mListener;

    public LocalHandler(Looper looper, Listener listener) {
        super(looper);
        mListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (null != mListener) {
            mListener.handleMsg(msg);
        }
    }

    public interface Listener {
        void handleMsg(Message msg);
    }
}
