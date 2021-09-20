package com.jx.androiddemo.testactivity.function.f3;

import android.content.Context;

import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class F3Presenter extends BaseRxPresenter<F3Contract.View> implements F3Contract.Presenter {
    private static final String TAG = "F3Presenter";

    private Context context;


    @Inject
    F3Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

}
