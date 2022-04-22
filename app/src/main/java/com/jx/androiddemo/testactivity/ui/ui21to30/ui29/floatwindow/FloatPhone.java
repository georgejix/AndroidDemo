package com.jx.androiddemo.testactivity.ui.ui21to30.ui29.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yhao on 17-11-14.
 * https://github.com/yhaolpz
 */

class FloatPhone extends FloatView {

    private final Context mContext;

    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private int mX, mY;
    private boolean isRemove = false;
    private PermissionListener mPermissionListener;
    private ChangeSizeListener mChangeSizeListener;

    FloatPhone(Context applicationContext, PermissionListener permissionListener) {
        mContext = applicationContext;
        mPermissionListener = permissionListener;
        mWindowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mLayoutParams.windowAnimations = 0;
    }

    @Override
    public void setSize(int width, int height) {
        mLayoutParams.width = width;
        mLayoutParams.height = height;
    }

    @Override
    public void setView(View view) {
        mView = view;
    }

    @Override
    public void setGravity(int gravity, int xOffset, int yOffset) {
        mLayoutParams.gravity = gravity;
        mLayoutParams.x = mX = xOffset;
        mLayoutParams.y = mY = yOffset;
    }

    @Override
    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            req();
        } else if (Miui.rom()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                req();
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                Miui.req(mContext, new PermissionListener() {
                    @Override
                    public void onSuccess() {
                        mWindowManager.addView(mView, mLayoutParams);
                        if (mPermissionListener != null) {
                            mPermissionListener.onSuccess();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mPermissionListener != null) {
                            mPermissionListener.onFail();
                        }
                    }
                });
            }
        } else {
            try {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                mWindowManager.addView(mView, mLayoutParams);
            } catch (Exception e) {
                mWindowManager.removeView(mView);
                req();
            }
        }
    }

    private void req() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        FloatActivity.request(mContext, new PermissionListener() {
            @Override
            public void onSuccess() {
                mWindowManager.addView(mView, mLayoutParams);
                if (mPermissionListener != null) {
                    mPermissionListener.onSuccess();
                }
            }

            @Override
            public void onFail() {
                if (mPermissionListener != null) {
                    mPermissionListener.onFail();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        isRemove = true;
        mWindowManager.removeView(mView);
    }

    @Override
    public void updateXY(int x, int y) {
        if (isRemove) return;
        mLayoutParams.x = mX = x;
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    void updateSizeStart() {
        if (isRemove) return;
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    void updateSizeEnd() {
        if (isRemove) return;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    public void updateSizeMove(int widthChange, int heightChange) {
        if (isRemove) return;
        if (null != mChangeSizeListener) {
            mChangeSizeListener.changeSize(widthChange, heightChange);
        }
    }

    @Override
    void clickShow() {
        if (isRemove) return;
        if (null != mChangeSizeListener) {
            mChangeSizeListener.clickShow();
        }
    }

    @Override
    void updateX(int x) {
        if (isRemove) return;
        mLayoutParams.x = mX = x;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    void updateY(int y) {
        if (isRemove) return;
        mLayoutParams.y = mY = y;
        mWindowManager.updateViewLayout(mView, mLayoutParams);
    }

    @Override
    int getX() {
        return mX;
    }

    @Override
    int getY() {
        return mY;
    }

    @Override
    void setChangeSizeListener(ChangeSizeListener changeSizeListener) {
        mChangeSizeListener = changeSizeListener;
    }

    @Override
    WindowManager getWindowManager() {
        return mWindowManager;
    }
}
