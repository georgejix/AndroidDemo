package com.jx.androiddemo.testactivity.ui.u1;

import android.content.Context;

import com.jx.androiddemo.testactivity.ui.u1.U1Contract;
import com.jx.androiddemo.presenter.BaseRxPresenter;

import javax.inject.Inject;

public class U1Presenter extends BaseRxPresenter<U1Contract.View> implements U1Contract.Presenter {
    private static final String TAG = "U1Presenter";

    private Context context;

    @Inject
    U1Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }
}
