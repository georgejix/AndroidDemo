package com.jx.androiddemo.testactivity.ui.ui21to30.ui29.floatwindow;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui29.PopScriptView;
import com.jx.androiddemo.tool.DisplayUtils;

/**
 * Created by yhao on 2017/12/22.
 * https://github.com/yhaolpz
 */

public class IFloatWindowImpl extends IFloatWindow {


    private FloatWindow.B mB;
    private FloatView mFloatView;
    private boolean isShow;
    private boolean once = true;
    private float downX;
    private float downY;
    private boolean mClick = false;
    private int screenWidth, titleBarHeight, screenHeight;
    private float mMinWidth, mMinHeight;
    private FloatLifecycle mFloatLifecycle;


    private IFloatWindowImpl() {

    }

    IFloatWindowImpl(FloatWindow.B b) {
        mB = b;
        mFloatView = new FloatPhone(b.mApplicationContext, mB.mPermissionListener);
        if (MoveType.script == b.mMoveType) {
            initTouchEvent();
        }
        mFloatView.setSize(mB.mWidth, mB.mHeight);
        mFloatView.setGravity(mB.gravity, mB.xOffset, mB.yOffset);
        mFloatView.setView(mB.mView);
        mFloatView.setChangeSizeListener(mB.mChangeSizeListener);
        mFloatLifecycle = new FloatLifecycle(mB.mApplicationContext, mB.mShow, mB.mActivities, new LifecycleListener() {
            @Override
            public void onShow() {
                show();
            }

            @Override
            public void onHide() {
                hide();
            }

            @Override
            public void onBackToDesktop() {
                if (!mB.mDesktopShow) {
                    hide();
                }
                if (mB.mViewStateListener != null) {
                    mB.mViewStateListener.onBackToDesktop();
                }
            }
        });
    }

    @Override
    public void show() {
        if (once) {
            mFloatView.init();
            once = false;
            isShow = true;
        } else {
            if (isShow) {
                return;
            }
            if (null != getView()) {
                getView().setVisibility(View.VISIBLE);
            }
            isShow = true;
        }
        if (mB.mViewStateListener != null) {
            mB.mViewStateListener.onShow();
        }
    }

    @Override
    public void hide() {
        if (once || !isShow) {
            return;
        }
        if (null != getView()) {
            getView().setVisibility(View.INVISIBLE);
        }
        isShow = false;
        if (mB.mViewStateListener != null) {
            mB.mViewStateListener.onHide();
        }
    }

    @Override
    public boolean isShowing() {
        return isShow;
    }

    @Override
    void dismiss() {
        mFloatView.dismiss();
        isShow = false;
        if (mB.mViewStateListener != null) {
            mB.mViewStateListener.onDismiss();
        }
    }

    @Override
    public void updateX(int x) {
        mB.xOffset = x;
        mFloatView.updateX(x);
    }

    @Override
    public void updateY(int y) {
        mB.yOffset = y;
        mFloatView.updateY(y);
    }

    @Override
    public void updateX(int screenType, float ratio) {
        mB.xOffset = (int) ((screenType == Screen.width ?
                Util.getScreenWidth(mB.mApplicationContext) :
                Util.getScreenHeight(mB.mApplicationContext)) * ratio);
        mFloatView.updateX(mB.xOffset);

    }

    @Override
    public void updateY(int screenType, float ratio) {
        mB.yOffset = (int) ((screenType == Screen.width ?
                Util.getScreenWidth(mB.mApplicationContext) :
                Util.getScreenHeight(mB.mApplicationContext)) * ratio);
        mFloatView.updateY(mB.yOffset);

    }

    @Override
    public int getX() {
        return mFloatView.getX();
    }

    @Override
    public int getY() {
        return mFloatView.getY();
    }

    @Override
    public void updateXY(int x, int y) {
        mFloatView.updateXY(x, y);
    }


    @Override
    public View getView() {
        return mB.mView;
    }

    @Override
    public boolean isPortrait() {
        return null != mFloatView && DisplayUtils.isPortrait(mFloatView.getWindowManager());
    }

    private void initTouchEvent() {
        screenWidth = Util.getScreenWidth(mB.mApplicationContext);
        titleBarHeight = DisplayUtils.getStatusBarHeight(mB.mApplicationContext);
        screenHeight = Util.getScreenHeight(mB.mApplicationContext);
        mMinWidth = mB.mApplicationContext.getResources().getDimension(R.dimen.pxtodp600);
        mMinHeight = mB.mApplicationContext.getResources().getDimension(R.dimen.pxtodp400);
        if (null != mB.mDragView) {
            mB.mDragView.setOnTouchListener(mDragTouchListener);
        }
        if (null != mB.mScaleView) {
            mB.mScaleView.setOnTouchListener(mScaleTouchListener);
        }
        if (null != mB.mShowView) {
            mB.mShowView.setOnTouchListener(mDragShowTouchListener);
        }
    }

    private final View.OnTouchListener mDragTouchListener = new View.OnTouchListener() {
        int newX, newY;
        float changeX, changeY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    changeX = event.getRawX() - downX;
                    changeY = event.getRawY() - downY;
                    if (mClick) {
                        if (Math.abs(changeX) > 10 || Math.abs(changeY) > 10) {
                            mClick = false;
                        }
                    }
                    newX = (int) (mFloatView.getX() + changeX);
                    newY = (int) (mFloatView.getY() + changeY);
                    if (newX > getScreenW() - getView().getWidth()) {
                        newX = getScreenW() - getView().getWidth();
                    } else if (newX < 0) {
                        newX = 0;
                    }
                    if (newY > getScreenH() - getView().getHeight()) {
                        newY = getScreenH() - getView().getHeight();
                    } else if (newY < -titleBarHeight) {
                        newY = -titleBarHeight;
                    }
                    mFloatView.updateXY(newX, newY);
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    break;
            }
            return true;
        }
    };
    private int mLastX, mLastY;
    private final View.OnTouchListener mDragShowTouchListener = new View.OnTouchListener() {
        int newX, newY;
        float changeX, changeY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mClick = true;
                    if (0 == mLastX) {
                        mLastX = getX();
                        mLastY = getY();
                    }
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    changeX = event.getRawX() - downX;
                    changeY = event.getRawY() - downY;
                    if (mClick) {
                        if (Math.abs(changeX) > 10 || Math.abs(changeY) > 10) {
                            mClick = false;
                        }
                    }
                    newX = (int) (mFloatView.getX() + changeX);
                    newY = (int) (mFloatView.getY() + changeY);
                    if (newX > getScreenW() - getView().getWidth()) {
                        newX = getScreenW() - getView().getWidth();
                    } else if (newX < 0) {
                        newX = 0;
                    }
                    if (newY > getScreenH() - getView().getHeight()) {
                        newY = getScreenH() - getView().getHeight();
                    } else if (newY < -titleBarHeight) {
                        newY = -titleBarHeight;
                    }
                    mFloatView.updateXY(newX, newY);
                    downX = event.getRawX();
                    downY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    if (mClick) {
                        mFloatView.clickShow();
                        mFloatView.updateXY(mLastX, mLastY);
                        mLastX = 0;
                    }
                default:
                    break;
            }
            return true;
        }
    };
    private final View.OnTouchListener mScaleTouchListener = new View.OnTouchListener() {
        int viewWidth, viewHeight;//view宽高
        int nowWidth, nowHeight;//缩放后宽高
        int originX, originY;
        int changeX, changeY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    viewWidth = getView().getWidth();
                    viewHeight = getView().getHeight();
                    originX = getX();
                    originY = getY();
                    mFloatView.updateSizeStart();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int rotation = 0;
                    if (getView() instanceof PopScriptView) {
                        rotation = (int) ((getView()).getRotation() % 360);
                    }
                    switch (rotation) {
                        case 0:
                            nowWidth = (int) (viewWidth + event.getRawX() - downX);
                            nowHeight = (int) (viewHeight + event.getRawY() - downY);
                            break;
                        case 90:
                            changeX = (int) (event.getRawX() - downX);
                            if (originX + changeX < 0) {
                                changeX = -originX;
                            } else if (viewWidth - changeX < mMinWidth) {
                                changeX = (int) (viewWidth - mMinWidth);
                            }
                            nowWidth = viewWidth - changeX;
                            nowHeight = (int) (viewHeight + event.getRawY() - downY);
                            mFloatView.updateX(originX + changeX);
                            break;
                        case 180:
                           /* nowWidth = (int) (viewWidth - event.getRawX() + downX);
                            nowHeight = (int) (viewHeight - event.getRawY() + downY);*/
                            break;
                        case 270:
                            changeY = (int) (event.getRawY() - downY);
                            if (changeY + originY < -titleBarHeight) {
                                changeY = -titleBarHeight - originY;
                            } else if (viewHeight - changeY < mMinHeight) {
                                changeY = (int) (viewHeight - mMinHeight);
                            }
                            nowWidth = (int) (viewWidth + event.getRawX() - downX);
                            nowHeight = viewHeight - changeY;
                            mFloatView.updateY(originY + changeY);
                            break;
                    }
                    if (getX() + nowWidth > getScreenW()) {
                        nowWidth = getScreenW() - getX();
                    }
                    if (getY() + nowHeight > getScreenH()) {
                        nowHeight = getScreenH() - getY();
                    }
                    mFloatView.updateSizeMove(nowWidth, nowHeight);
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    mFloatView.updateSizeEnd();
                    break;
            }
            return true;
        }
    };

    private int getScreenW() {
        if (null == mB || null == mB.mApplicationContext) {
            return screenWidth;
        }
        return DisplayUtils.isPortrait(mFloatView.getWindowManager()) ? screenWidth : (screenHeight + titleBarHeight);
    }

    private int getScreenH() {
        if (null == mB || null == mB.mApplicationContext) {
            return screenHeight;
        }
        return DisplayUtils.isPortrait(mFloatView.getWindowManager()) ? screenHeight : (screenWidth - titleBarHeight);
    }
}
