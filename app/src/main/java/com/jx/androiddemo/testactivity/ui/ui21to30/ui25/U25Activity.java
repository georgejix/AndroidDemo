package com.jx.androiddemo.testactivity.ui.ui21to30.ui25;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U25Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.adv_viewpager2)
    ViewPager2 mViewPager2;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mCanScroll = true;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u25;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        List<String> tempAdvList = new ArrayList();
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffile01.16sucai.com%2Fd%2Ffile%2F2011%2F1007%2F20111007124744976.jpg&refer=http%3A%2F%2Ffile01.16sucai.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=6c70e33c44c5b0c2b8e52299787df171");
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2014%2F0427%2F071875652097059bbbffe106f9ce3a93.jpg&refer=http%3A%2F%2Ffile02.16sucai.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=a3864e40c07c07bc5e4aaf30a1a5b278");
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp05%2F1Z9291J4442Y1-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=f79003c73c175773791316899df4da3d");

        List<String> advList = new ArrayList();
        advList.add(tempAdvList.get(tempAdvList.size() - 1));
        advList.addAll(tempAdvList);
        advList.add(tempAdvList.get(0));


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext);
        viewPagerAdapter.addDataAll(advList);
        mViewPager2.setAdapter(viewPagerAdapter);
        mViewPager2.setCurrentItem(1, false);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (0.0 == positionOffset) {
                    if (0 == position) {
                        mViewPager2.setCurrentItem(advList.size() - 2, false);
                        Log.d(TAG, "jump from " + position + " to " + (advList.size() - 2));
                    } else if (advList.size() - 1 == position) {
                        mViewPager2.setCurrentItem(1, false);
                        Log.d(TAG, "jump from " + position + " to 1");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCanScroll = ViewPager2.SCROLL_STATE_DRAGGING != state;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mTimerTask) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mCanScroll && null != mViewPager2) {
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
                }
            }
        };
        mTimer.schedule(mTimerTask, 3000, 3000);
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

}