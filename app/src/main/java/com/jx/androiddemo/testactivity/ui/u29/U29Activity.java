package com.jx.androiddemo.testactivity.ui.u29;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.activity.main.MainActivity;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.FloatWindow;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.MoveType;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.PermissionListener;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.ViewStateListener;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U29Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.tv_show)
    View mShow;
    @BindView(R.id.tv_show2)
    View mShow2;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u29;
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
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

        //点击
        RxView.clicks(mShow)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showPop("12345678901234567890");
                });
        RxView.clicks(mShow2)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showPop("abcdefghigklmnopqrstuvwxyz");
                });
    }

    private void showPop(String content) {
        if (null == FloatWindow.get() || null == FloatWindow.get().getView()) {
            //不存在才新建
            PopScriptView view = new PopScriptView(getApplicationContext());
            FloatWindow
                    .with(getApplicationContext())
                    .setView(view)
                    //.setWidth(Screen.width, 0.94f) //设置悬浮控件宽高
                    //.setHeight(Screen.height, 0.47f)
                    .setX(getResources().getDimensionPixelOffset(R.dimen.pxtodp20))
                    .setY(getResources().getDimensionPixelOffset(R.dimen.pxtodp20))
                    .setMoveType(MoveType.script)
                    .setMoveStyle(500, new BounceInterpolator())
                    .setFilter(false, MainActivity.class)
                    .setViewStateListener(mViewStateListener)
                    .setPermissionListener(mPermissionListener)
                    .setDesktopShow(true)
                    .setDragView(view.getDragView())
                    .setScaleView(view.getScaleView())
                    .setShowView(view.getShowView())
                    .setChangeSizeListener(view.getChangeSizeListener())
                    .build();
        }
        if (FloatWindow.get().getView() instanceof PopScriptView) {
            //设置台本，重置状态
            PopScriptView popScriptView = (PopScriptView) FloatWindow.get().getView();
            popScriptView.setScript(content);
        }
        FloatWindow.get().show();
    }

    private final PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private final ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
            if (null != FloatWindow.get() && FloatWindow.get().getView() instanceof PopScriptView) {
                //隐藏设置状态为结束
                ((PopScriptView) FloatWindow.get().getView()).finish();
            }
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
}