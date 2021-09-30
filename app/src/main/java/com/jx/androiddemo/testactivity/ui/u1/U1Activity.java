package com.jx.androiddemo.testactivity.ui.u1;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

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
    @BindView(R.id.tv_change_full_screen3)
    TextView tv_change_full_screen3;
    @BindView(R.id.tv_change_full_screen4)
    TextView tv_change_full_screen4;
    @BindView(R.id.tv_change_full_screen5)
    TextView tv_change_full_screen5;

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
        //api30判断导航栏显示与否
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (null != getWindow() && null != getWindow().getDecorView()) {
                getWindow().getDecorView().setOnApplyWindowInsetsListener((v, insets) -> {
                    Log.d(TAG, "statusBars" + insets.isVisible(WindowInsets.Type.statusBars()));
                    return insets;
                });
            }
        }
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
                    showActionAndNavNew();
                });
        RxView.clicks(tv_change_full_screen3)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    hideActionAndNavNew();
                });
        RxView.clicks(tv_change_full_screen4)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showSysView();
                });
        RxView.clicks(tv_change_full_screen5)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    hideSysView();
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

    //切换全屏,显示好像有问题！
    private void showActionAndNavNew() {
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(tv_change_full_screen2);
        if (null == controller) {
            return;
        }

        //显示状态栏
        controller.show(WindowInsetsCompat.Type.statusBars());
        //显示导航栏
        controller.show(WindowInsetsCompat.Type.navigationBars());
        //导航栏文字颜色
        controller.setAppearanceLightNavigationBars(false);
        //显示键盘
        controller.show(WindowInsetsCompat.Type.ime());

    }

    private void hideActionAndNavNew() {
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(tv_change_full_screen2);
        if (null == controller) {
            return;
        }

        //隐藏状态栏
        controller.hide(WindowInsetsCompat.Type.statusBars());
        //隐藏导航栏
        controller.hide(WindowInsetsCompat.Type.navigationBars());
        //导航栏文字颜色
        controller.setAppearanceLightNavigationBars(true);
        //隐藏键盘
        controller.hide(WindowInsetsCompat.Type.ime());
    }

    private void showSysView() {
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(tv_change_full_screen2);
        if (null == controller) {
            return;
        }
        //操作所有系统栏
        controller.show(WindowInsetsCompat.Type.systemBars());
    }

    private void hideSysView() {
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(tv_change_full_screen2);
        if (null == controller) {
            return;
        }
        //操作所有系统栏
        controller.hide(WindowInsetsCompat.Type.systemBars());
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