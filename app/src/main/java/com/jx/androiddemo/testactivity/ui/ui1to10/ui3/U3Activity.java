package com.jx.androiddemo.testactivity.ui.ui1to10.ui3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U3Activity extends BaseMvpActivity<U3Presenter> implements U3Contract.View {
    private final String TAG = "U3Activity";

    @BindView(R.id.tv_show_pop)
    TextView tv_show_pop;

    @BindView(R.id.tv_to_3_1)
    TextView tv_to_3_1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u3;
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
        RxView.clicks(tv_show_pop)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showPop();
                });
        RxView.clicks(tv_to_3_1)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    startActivity(new Intent(this, U3_1Activity.class));
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //android.os.Debug.stopMethodTracing();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    private View editPop;
    private PopupWindow editPopupWindow;
    private PopupWindowListener popClickListener;
    private ViewHolder viewHolder;

    private void showPop() {
        if (null == editPop) {
            editPop = LayoutInflater.from(this).inflate(R.layout.pop_u3, null);
            if (null == popClickListener) {
                popClickListener = new PopupWindowListener();
            }
            viewHolder = new ViewHolder();
            if (null != viewHolder) {
            }
        }
        if (null == editPopupWindow) {
            editPopupWindow = new PopupWindow(editPop, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            // 设置PopupWindow的背景
            editPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            editPopupWindow.setOutsideTouchable(false);
            // 设置PopupWindow是否能响应点击事件
            editPopupWindow.setTouchable(true);
            editPopupWindow.setOnDismissListener(() -> setBackgroundAlpha(1f));
            editPopupWindow.setFocusable(true);
            editPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        if (!editPopupWindow.isShowing()) {
            setBackgroundAlpha(0.7f);
            editPopupWindow.showAtLocation(tv_show_pop, Gravity.CENTER, 0, 0);
            //popupWindow.showAsDropDown(titleBarView.getRight1View(), 0, 0);
        }
    }


    class ViewHolder {
    }

    class PopupWindowListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            }
        }
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
    }

    //关闭popupWindow.setOutsideTouchable，重写此方法，否则pop显示情况下点击+，pop不会消失
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (editPopupWindow != null && editPopupWindow.isShowing()) {
            editPopupWindow.dismiss();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (editPopupWindow != null && editPopupWindow.isShowing()) {
                editPopupWindow.dismiss();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}