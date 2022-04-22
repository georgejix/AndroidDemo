package com.jx.androiddemo.testactivity.ui.ui21to30.ui27;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.jx.androiddemo.R;


public class PopService extends Service {
    private final static String TAG = "PopService";
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View mPopLayout;
    private Messenger mReplyMessenger;
    private Messenger mSendMessenger;
    private int mMaxWidth, mMaxHeight;

    public final static int MSG_SHOW_HIDE_BTN = 1001;


    @Override
    public void onCreate() {
        super.onCreate();
        mMaxWidth = getScreenWidth2(this);
        mMaxHeight = getScreenHeight2(this);
    }

    private void showFloatingWindow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.gravity = Gravity.TOP | Gravity.END;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.x = 100;
            layoutParams.y = 100;


            mPopLayout = LayoutInflater.from(this).inflate(R.layout.layout_u27_pop_view, null);
            windowManager.addView(mPopLayout, layoutParams);
            mPopLayout.setOnTouchListener(new FloatingOnTouchListener());
        }
    }

    private void hideFloatingWindow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            windowManager.removeView(mPopLayout);
            mPopLayout = null;
        }
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x - movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    if (layoutParams.x < 0) {
                        layoutParams.x = 0;
                    } else if (layoutParams.x > mMaxWidth - view.getWidth()) {
                        layoutParams.x = mMaxWidth - view.getWidth();
                    }
                    if (layoutParams.y < 0) {
                        layoutParams.y = 0;
                    } else if (layoutParams.y > mMaxHeight - view.getHeight()) {
                        layoutParams.y = mMaxHeight - view.getHeight();
                    }
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    private void sendStrMsg(String str) {
        Message msg = new Message();
        msg.what = U27Activity.MSG_SHOW_MSG;
        Bundle bundle = new Bundle();
        bundle.putString("msgStr", str);
        msg.setData(bundle);
        sendMsg(msg);
    }

    //发送消息
    private void sendMsg(Message msg) {
        if (null == mSendMessenger || null == msg) {
            return;
        }
        try {
            mSendMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //接收消息
    private void handleMsg(Message msg) {
        if (null == msg) {
            return;
        }
        switch (msg.what) {
            case MSG_SHOW_HIDE_BTN:
                if (null == mPopLayout) {
                    showFloatingWindow();
                } else {
                    hideFloatingWindow();
                }
                mSendMessenger = msg.replyTo;
                sendStrMsg("123");
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (null == mReplyMessenger) {
            HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName());
            handlerThread.start();
            mReplyMessenger = new Messenger(new LocalHandler(handlerThread.getLooper(), this::handleMsg));
        }
        return mReplyMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public static int getScreenWidth2(Context context) {
        if (null == context) {
            return 0;
        }
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight2(Context context) {
        if (null == context) {
            return 0;
        }
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
