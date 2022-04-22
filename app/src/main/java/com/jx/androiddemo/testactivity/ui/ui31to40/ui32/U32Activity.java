package com.jx.androiddemo.testactivity.ui.ui31to40.ui32;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U32Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View
{

    private ViewPager2 vp;

    private U32Fragment f0;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ");
    }

    @Override
    protected void initInject()
    {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout()
    {
        return R.layout.activity_u32;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData()
    {
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if (null == vp)
        {
            initView();
            initListener();
        }
        Log.d(TAG, null == vp ? "vp==null" : "vp!=null ");
        Log.d(TAG, null == f0 ? "f0==null" : "f0!=null ");
    }

    private void initView()
    {
        Log.d(TAG, "initView");
        vp = findViewById(R.id.vp);
        setVpAdapter();
        setVpAdapter(); //在页面后台被回收重新打开时，连续调用setAdapter两次，可以刷新当前viewpager2中的fragment，调用一次时，当前fragment不会重新new
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                Log.d(TAG, "vp change to " + position);
            }
        });
    }

    private void setVpAdapter()
    {
        vp.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle())
        {
            @Override
            public int getItemCount()
            {
                return 3;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position)
            {
                U32Fragment u32Fragment = new U32Fragment();
                u32Fragment.setText(position + "");
                Log.d(TAG, "new fragment " + position);
                if (0 == position)
                {
                    Log.d(TAG, "new f0");
                    f0 = u32Fragment;
                }
                return u32Fragment;
            }
        });
    }

    public void refresh()
    {
        if (null == f0)
        {
            setVpAdapter();
        }
    }

    @SuppressLint("CheckResult")
    private void initListener()
    {
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