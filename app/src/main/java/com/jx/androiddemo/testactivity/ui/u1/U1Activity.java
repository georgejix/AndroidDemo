package com.jx.androiddemo.testactivity.ui.u1;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * 全屏，非全屏切换
 * 子线程添加view
 */
public class U1Activity extends BaseMvpActivity<U1Presenter> implements U1Contract.View {

    @BindView(R.id.tv_change_full_screen1)
    TextView tv_change_full_screen1;

    @BindView(R.id.tv_change_full_screen2)
    TextView tv_change_full_screen2;

    @BindView(R.id.tv_add_view_in_child_thread)
    TextView tv_add_view_in_child_thread;

    private HandlerThread mHandlerThread;

    private Handler mBackHandler;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u1;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        RxView.clicks(tv_change_full_screen1)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    changeFullScreenOld();
                });
        RxView.clicks(tv_change_full_screen2)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    changeFullScreenNew();
                });
        RxView.clicks(tv_add_view_in_child_thread)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    addViewInChildThread();
                });
    }

    //切换全屏
    private void changeFullScreenOld() {
        int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (options != getWindow().getDecorView().getSystemUiVisibility()) {
            getWindow().getDecorView().setSystemUiVisibility(options);
        } else {
            int o = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(o);
        }
    }

    //切换全屏
    private void changeFullScreenNew() {
        /*WindowInsetsControllerCompat controllerCompat = ViewCompat.getWindowInsetsController(tv_change_full_screen2);
        if (!mIsFullScreen) {
            controllerCompat.hide(WindowInsetsCompat.Type.systemBars());
            mIsFullScreen = true;
        } else {
            controllerCompat.show(WindowInsetsCompat.Type.systemBars());
            mIsFullScreen = false;
        }*/
    }

    //子线程添加view
    private void addViewInChildThread() {
        if (null == mHandlerThread) {
            mHandlerThread = new HandlerThread("childThread");
            mHandlerThread.start();
        }
        if (null == mBackHandler) {
            mBackHandler = new Handler(mHandlerThread.getLooper(), msg -> {
                switch (msg.what) {
                    case 0:
                        addWindView();
                        break;
                }
                return false;
            });
        }
        mBackHandler.sendEmptyMessage(0);
    }

    //子线程添加view
    private void addWindView() {
        TextView tx = new TextView(U1Activity.this);
        tx.setText("今天天气很好哦！");
        tx.setTextColor(getResources().getColor(R.color.color_white));
        tx.setBackgroundColor(getResources().getColor(R.color.red));
        tx.setGravity(Gravity.CENTER);
        WindowManager wm = getWindowManager();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                250, 150, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE);
        wm.addView(tx, params);
    }
}