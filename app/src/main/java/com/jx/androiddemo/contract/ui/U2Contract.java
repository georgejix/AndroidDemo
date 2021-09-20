package com.jx.androiddemo.contract.ui;

import com.jx.androiddemo.BaseView;
import com.jx.androiddemo.presenter.BasePresenter;

import java.util.List;

public class U2Contract {
    public interface View extends BaseView {
        void onFinishLoading();

        void onFinishLoadingMore();

        void noMoreData();

        void refreshRecyclerView();
    }

    public interface Presenter extends BasePresenter<View> {
        void refreshData();

        void loadMoreData();

        List<String> getData();
    }
}
