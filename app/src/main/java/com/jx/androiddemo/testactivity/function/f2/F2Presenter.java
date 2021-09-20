package com.jx.androiddemo.testactivity.function.f2;

import android.content.Context;

import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class F2Presenter extends BaseRxPresenter<F2Contract.View> implements F2Contract.Presenter {
    private static final String TAG = "F2Presenter";

    private Context context;


    @Inject
    F2Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

}
