package com.jx.androiddemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jx.androiddemo.di.component.ActivityComponent;
import com.jx.androiddemo.di.component.DaggerActivityComponent;
import com.jx.androiddemo.di.module.ActivityModule;
import com.jx.androiddemo.event.NoticeEvent;
import com.jx.androiddemo.presenter.BasePresenter;
import com.jx.androiddemo.presenter.BaseRxPresenter;
import com.jx.androiddemo.tool.ToastUtil;
import com.jx.arch.util.StatusBarUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WangTao
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseAppActivity implements BaseView
{
    @Inject
    protected T mPresenter;

    protected Activity mContext;

    private Unbinder mUnBinder;

    public String TAG = getClass().getSimpleName();

    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().mRefWatcher.watch(this);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        //设置状态栏颜色
        //setStatusBarColor(getResources().getColor(R.color.head_view_bg));
        mContext = this;
        initInject();
        if (mPresenter != null)
        {
            mPresenter.attachView(this);
        }
        initEventAndData();

        //刘海屏横屏显示，左边留白修复
        /*if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }*/
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife()
    {
        return this.bindToLifecycle();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //DeviceUtils.NavigationBarStatusBar(getWindow());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //DeviceUtils.NavigationBarStatusBar(getWindow());
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
        {
            mPresenter.detachView();
        }
        if (mUnBinder != null)
        {
            mUnBinder.unbind();
        }
        if (mPresenter != null && mPresenter instanceof BaseRxPresenter)
        {
            ((BaseRxPresenter) mPresenter).doDispose();
        }
    }

    protected void setStatusBarColor(int color)
    {
        StatusBarUtil.setColor(this, color, 0);
    }

    /**
     * 添加注解
     */
    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();

    protected ActivityComponent getActivityComponent()
    {
        return DaggerActivityComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule()
    {
        return new ActivityModule(this);
    }

    @Override
    public void showMsg(String msg)
    {
        ToastUtil.showTextToast(msg);
    }

    @Override
    public void showProgressDialog(int tag, boolean canCancel)
    {
    }

    @Override
    public void hiddenProgressDialog()
    {
    }

    @Override
    public void onEventMainThread(NoticeEvent event)
    {
    }
}