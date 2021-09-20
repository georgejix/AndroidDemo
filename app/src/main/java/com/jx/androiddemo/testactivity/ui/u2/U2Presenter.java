package com.jx.androiddemo.testactivity.ui.u2;

import android.content.Context;

import com.jx.androiddemo.testactivity.ui.u2.U2Contract;
import com.jx.androiddemo.presenter.BaseRxPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class U2Presenter extends BaseRxPresenter<U2Contract.View> implements U2Contract.Presenter {
    private static final String TAG = "U2Presenter";

    private Context context;

    private int mPageNo;

    private List<String> mDataList = new ArrayList<>();

    @Inject
    U2Presenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

    @Override
    public void refreshData() {
        mPageNo = 0;
        mDataList.clear();
        for (int i = 0; i < 15; i++) {
            mDataList.add(Integer.toString(i));
        }
        getView().onFinishLoading();
        getView().refreshRecyclerView();
    }

    @Override
    public void loadMoreData() {
        if (mDataList.size() >= 30) {
            getView().noMoreData();
        } else {
            int end = mDataList.size() + 15;
            for (int i = mDataList.size(); i < end; i++) {
                mDataList.add(Integer.toString(i));
            }
            getView().onFinishLoadingMore();
            getView().refreshRecyclerView();
        }
    }

    @Override
    public List<String> getData() {
        return mDataList;
    }
}
