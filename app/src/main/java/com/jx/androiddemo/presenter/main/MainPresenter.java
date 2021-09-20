package com.jx.androiddemo.presenter.main;

import android.content.Context;

import com.jx.androiddemo.testactivity.function.f1.F1Activity;
import com.jx.androiddemo.testactivity.function.f2.F2Activity;
import com.jx.androiddemo.testactivity.ui.u1.U1Activity;
import com.jx.androiddemo.testactivity.ui.u2.U2Activity;
import com.jx.androiddemo.bean.main.MainPageBean;
import com.jx.androiddemo.contract.main.MainContract;
import com.jx.androiddemo.presenter.BaseRxPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends BaseRxPresenter<MainContract.View> implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";

    private Context context;

    private List<MainPageBean> mMainPageList;


    @Inject
    MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

    @Override
    public List<MainPageBean> getMainPageList() {
        if (null == mMainPageList) {
            mMainPageList = new ArrayList<>();
            mMainPageList.add(new MainPageBean("简单线程", F1Activity.class));
            mMainPageList.add(new MainPageBean("aidl", F2Activity.class));


            mMainPageList.add(new MainPageBean("简单ui", U1Activity.class));
            mMainPageList.add(new MainPageBean("上拉加载，下拉刷新", U2Activity.class));
        }
        return mMainPageList;
    }
}
