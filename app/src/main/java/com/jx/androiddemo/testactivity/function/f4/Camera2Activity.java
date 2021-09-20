package com.jx.androiddemo.testactivity.function.f4;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class Camera2Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final static String TAG = "Camera2Activity";

    @BindView(R.id.layout_preview)
    SurfaceView mPreviceSurface;

    @BindView(R.id.textview_facing)
    TextView mFacingTextView;

    private int mBackCameraId, mFrontCameraId, mCurrentCameraId;
    private int mBackCameraOrientation, mFrontCameraOrientation, mCurrentCameraOrientation;
    private Camera mCamera;
    private SurfaceCallback mSurfaceCallback;
    private static Object mOpenCameraLock = new Object();
    private int mScreenDegree, mLastScreenDegree = -1;
    private OrientationEventListener mOrientationListener;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f4_camera2;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    protected void initView() {
        getCameraParams();
        init(null);
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        RxView.clicks(mFacingTextView)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    toggleFacing();
                });
    }

    //存参数
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mCurrentCameraId", mCurrentCameraId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mCamera) {
            mCamera.stopPreview();
        }
        mOrientationListener.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mCamera) {
            mCamera.startPreview();
        }
        mOrientationListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCamera) {
            mCamera.release();
        }
    }

    //获取方向数值
    private void prepareOpenCamera(boolean isback) {
        if (isback) {
            mCurrentCameraOrientation = (mBackCameraOrientation - mScreenDegree + 360) % 360;
            openCamera();
        } else {
            mCurrentCameraOrientation = (mFrontCameraOrientation - mScreenDegree + 180 + 360) % 360;
            openCamera();
        }
    }

    //打开摄像头
    private void openCamera() {
        Log.d(TAG, "openCamera");
        if (null != mCamera) {
            mCamera.stopPreview();
            mCamera.release();
        }
        mCamera = Camera.open(mCurrentCameraId);
        mCamera.setDisplayOrientation(mCurrentCameraOrientation);

        mCamera.startPreview();
        if (null == mSurfaceCallback) {
            mSurfaceCallback = new SurfaceCallback();
            mPreviceSurface.getHolder().addCallback(mSurfaceCallback);
        }
        try {
            mCamera.setPreviewDisplay(mPreviceSurface.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //切换前后摄像头
    private void toggleFacing() {
        synchronized (mOpenCameraLock) {
            if (mBackCameraId == mCurrentCameraId) {
                mCurrentCameraId = mFrontCameraId;
                prepareOpenCamera(true);
            } else {
                mCurrentCameraId = mBackCameraId;
                prepareOpenCamera(false);
            }
        }
    }

    //获取前后摄像头
    private void getCameraParams() {
        int cameraNumber = Camera.getNumberOfCameras();
        Log.d(TAG, "cameranumber=" + cameraNumber);
        for (int i = 0; i < cameraNumber; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (null == cameraInfo)
                continue;
            if (Camera.CameraInfo.CAMERA_FACING_BACK == cameraInfo.facing) {
                mBackCameraId = i;
                mBackCameraOrientation = cameraInfo.orientation;
            } else if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing) {
                mFrontCameraId = i;
                mFrontCameraOrientation = cameraInfo.orientation;
            }
        }
    }

    private void init(Bundle bundle) {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                mScreenDegree = 0;
                break;
            case Surface.ROTATION_90:
                mScreenDegree = 90;
                break;
            case Surface.ROTATION_180:
                mScreenDegree = 180;
                break;
            case Surface.ROTATION_270:
                mScreenDegree = 270;
                break;
        }
        onDegreeChanged();
        if (null != bundle) {
            synchronized (mOpenCameraLock) {
                mCurrentCameraId = bundle.getInt("mCurrentCameraId");
            }
        } else {
            synchronized (mOpenCameraLock) {
                mCurrentCameraId = mBackCameraId;
            }
        }
        prepareOpenCamera(true);
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return; // 手机平放时，检测不到有效的角度
                }
                // 只检测是否有四个角度的改变
                if (orientation > 350 || orientation < 10) {
                    // 0度：手机默认竖屏状态（home键在正下方）
                    //mScreenDegree = 0;
                } else if (orientation > 80 && orientation < 100) {
                    // 90度：手机顺时针旋转90度横屏（home建在左侧）
                    mScreenDegree = 270;
                } else if (orientation > 170 && orientation < 190) {
                    // 180度：手机顺时针旋转180度竖屏（home键在上方）
                    //mScreenDegree = 180;
                } else if (orientation > 260 && orientation < 280) {
                    // 270度：手机顺时针旋转270度横屏，（home键在右侧）
                    mScreenDegree = 90;
                }
                //只针对横屏间互相切换
                if (270 == mLastScreenDegree || 90 == mLastScreenDegree) {
                    onDegreeChanged();
                }
            }
        };
    }

    private void onDegreeChanged() {
        if (-1 == mLastScreenDegree) {
            mLastScreenDegree = mScreenDegree;
            return;
        }
        if (mLastScreenDegree == mScreenDegree) {
            return;
        }
        prepareOpenCamera(mCurrentCameraId == mBackCameraId);
        mLastScreenDegree = mScreenDegree;
    }

    class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            if (null != mCamera) {
                mCamera.startPreview();
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    Log.d(TAG, e + "");
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");

            if (null != mCamera) {
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    Log.d(TAG, e + "");
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
        }
    }
}
