package com.jx.androiddemo.presenter;

import com.jx.androiddemo.BaseView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/3/28.
 */

public abstract class BaseRxPresenter<T extends BaseView> implements BasePresenter<T>
{
    protected Reference<T> mView;

    public abstract void doDispose();

    @Override
    public void attachView(T view)
    {
        mView = new WeakReference<>(view);
    }

    protected T getView()
    {
        if (mView == null)
        {
            return null;
        }
        return mView.get();//获取View
    }

    public boolean isViewAttached()//判断是否与View建立了关联
    {
        return mView != null && mView.get() != null;
    }

    @Override
    public void detachView()
    {
        if (mView != null)
        {
            mView.clear();
            mView = null;
        }
    }
}
