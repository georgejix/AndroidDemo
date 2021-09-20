package com.jx.androiddemo.testactivity.ui.u3;

import android.content.Context;

import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class U3Presenter extends BaseRxPresenter<U3Contract.View> implements U3Contract.Presenter {
    private static final String TAG = "U3Presenter";

    private Context context;


    @Inject
    U3Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

}
