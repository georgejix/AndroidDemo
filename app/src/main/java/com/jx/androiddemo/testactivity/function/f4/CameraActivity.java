package com.jx.androiddemo.testactivity.function.f4;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.IOException;

import butterknife.BindView;

public class CameraActivity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "CameraActivity";

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    private SurfaceViewCallBack surfaceViewCallBack;
    private Camera mCamera;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f4_camera;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
    }

    protected void initView() {

        mCamera = Camera.open(0);
        surfaceViewCallBack = new SurfaceViewCallBack();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceViewCallBack);

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPreviewAndFreeCamera();
    }

    /**
     * When this function returns, mCamera will be null.
     */
    private void stopPreviewAndFreeCamera() {

        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();

            mCamera = null;
        }
    }

    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                surfaceView.postDelayed(doAutoFocus, 1000);
            }
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            try {
                mCamera.autoFocus(autoFocusCB);
            } catch (Exception e) {
            }
        }
    };

    class SurfaceViewCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.d(TAG, e + "");
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.d(TAG, e + "");
                e.printStackTrace();
            }
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCB);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
