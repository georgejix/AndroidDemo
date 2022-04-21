package com.jx.androiddemo.testactivity.ui.u26;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.jx.androiddemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SlideshowView extends FrameLayout {
    private final String TAG = "SlideshowView";
    private Context mContext;
    private ViewPager2 mViewPager2;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mCanScroll = true;
    private List<String> advList = new ArrayList();
    private int mRealSize;
    private SlideshowPicAdapter mSlideshowPicAdapter;
    private Listener mListener;

    public SlideshowView(Context context) {
        super(context);
        initView(context);
    }

    public SlideshowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SlideshowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setData(List<String> data) {
        advList.clear();
        if (null != data && data.size() > 0) {
            advList.add(data.get(data.size() - 1));
            advList.addAll(data);
            advList.add(data.get(0));
        }
        mRealSize = null != data ? data.size() : 0;
        mSlideshowPicAdapter.addDataAll(advList);
        mSlideshowPicAdapter.notifyDataSetChanged();
        if (advList.size() > 0) {
            mViewPager2.setCurrentItem(1, false);
        }
        startTimer();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_u26_slideshow_view, null, false);
        mViewPager2 = view.findViewById(R.id.adv_viewpager2);
        mSlideshowPicAdapter = new SlideshowPicAdapter(mContext);
        mSlideshowPicAdapter.addDataAll(advList);
        mViewPager2.setAdapter(mSlideshowPicAdapter);
        mViewPager2.setOffscreenPageLimit(3);
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

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (null != mListener && advList.size() >= 3) {
                    mViewPager2.post(() -> mListener.change(((position - 1) % (mRealSize)), mRealSize));
                }
            }
        });
        addView(view);
    }

    public void startTimer() {
        if (null != mTimer) {
            stopTimer();
        }
        if (0 == mRealSize) {
            Log.d(TAG, "startTimer return");
            return;
        }
        Log.d(TAG, "startTimer");
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mCanScroll && null != mViewPager2) {
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1 <= advList.size() - 1 ?
                            mViewPager2.getCurrentItem() + 1 : mViewPager2.getCurrentItem());
                }
            }
        };
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

    public void stopTimer() {
        Log.d(TAG, "stopTimer");
        if (null != mTimerTask) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public interface Listener {
        void change(int position, int totalCount);
    }
}
