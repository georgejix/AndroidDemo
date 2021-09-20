package com.jx.androiddemo.presenter.main;

import android.content.Context;

import com.jx.androiddemo.contract.main.LaunchContract;
import com.jx.androiddemo.contract.main.MainContract;
import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class LaunchPresenter extends BaseRxPresenter<LaunchContract.View> implements LaunchContract.Presenter
{
    private static final String TAG = "LaunchPresenter";

    private Context context;

    @Inject
    LaunchPresenter(Context context)
    {
        this.context = context;
    }

    @Override
    public void doDispose()
    {

    }
}
