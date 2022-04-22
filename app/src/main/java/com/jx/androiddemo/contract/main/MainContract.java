package com.jx.androiddemo.contract.main;

import com.jx.androiddemo.BaseView;
import com.jx.androiddemo.bean.main.MainPageBean;
import com.jx.androiddemo.presenter.BasePresenter;

import java.util.List;

public class MainContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {
        List<MainPageBean> getLeftList();

        List<MainPageBean> getRightList();
    }
}
