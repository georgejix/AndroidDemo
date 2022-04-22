package com.jx.androiddemo.testactivity.ui.ui1to10.ui9;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import androidx.core.view.VelocityTrackerCompat;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U9Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "U9Activity";

    private VelocityTracker mVelocityTracker = null;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u9;
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
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    int index;
    int pointerId;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //MotionEventCompat.getActionMasked(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                index = event.getActionIndex();
                pointerId = event.getPointerId(index);

                if (null == mVelocityTracker) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);
                //可以根据index或者pointid获取对应点击事件（主事件、副事件）的数值
                Log.d(TAG, "xx:" + VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
                Log.d(TAG, "vy:" + mVelocityTracker.getYVelocity());
                Log.d(TAG, "vy:" + mVelocityTracker.getYVelocity(pointerId));
                if (-1 != event.findPointerIndex(pointerId)) {
                    Log.d(TAG, "x=" + event.getX(event.findPointerIndex(pointerId)));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }
}