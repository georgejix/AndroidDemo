package com.jx.androiddemo.testactivity.function.empty;

import android.content.Context;

import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class EmptyPresenter extends BaseRxPresenter<EmptyContract.View> implements EmptyContract.Presenter {
    private static final String TAG = "EmptyPresenter";

    private Context context;


    @Inject
    EmptyPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

}
