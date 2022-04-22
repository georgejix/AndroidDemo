package com.jx.androiddemo.testactivity.function.f1to10.f1;

import android.content.Context;

import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class F1Presenter extends BaseRxPresenter<F1Contract.View> implements F1Contract.Presenter {
    private static final String TAG = "F1Presenter";

    private Context context;


    @Inject
    F1Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

}
