package com.jx.androiddemo.contract.main;

import com.jx.androiddemo.BaseView;
import com.jx.androiddemo.presenter.BasePresenter;

public class MainContract
{
    public interface View extends BaseView
    {

    }

    public interface Presenter extends BasePresenter<View>
    {

    }
}
