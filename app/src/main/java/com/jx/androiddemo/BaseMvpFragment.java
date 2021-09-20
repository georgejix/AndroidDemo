package com.jx.androiddemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.jx.androiddemo.di.component.DaggerFragmentComponent;
import com.jx.androiddemo.di.component.FragmentComponent;
import com.jx.androiddemo.di.module.FragmentModule;
import com.jx.androiddemo.event.NoticeEvent;
import com.jx.androiddemo.presenter.BasePresenter;
import com.jx.androiddemo.presenter.BaseRxPresenter;
import com.jx.androiddemo.tool.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WangTao
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseAppFragment implements BaseView
{
    @Inject
    protected T mPresenter;

    protected View mView;

    protected Activity mActivity;

    protected Context mContext;

    private Unbinder mUnBinder;

    private boolean mInBack = true;

    @Override
    public void onAttach(Context context)
    {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    protected FragmentComponent getFragmentComponent()
    {
        return DaggerFragmentComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule()
    {
        return new FragmentModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null)
        {
            mUnBinder = ButterKnife.bind(this, view);
            if (mPresenter != null)
            {
                mPresenter.attachView(this);
            }
            initEventAndData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
//        super.onSaveInstanceState(outState);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife()
    {
        return this.bindToLifecycle();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
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

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

    @Override
    public void showMsg(String msg)
    {
        ToastUtil.showTextToast(mContext, msg);
    }

    @Override
    public void onEventMainThread(NoticeEvent event)
    {
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
    public void onResume()
    {
        super.onResume();
        mInBack = false;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mInBack = true;
    }

    public boolean isInBack()
    {
        return mInBack;
    }
}
