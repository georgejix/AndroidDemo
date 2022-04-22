package com.jx.androiddemo.testactivity.empty;

import com.jx.androiddemo.BaseView;
import com.jx.androiddemo.presenter.BasePresenter;

public class EmptyContract
{
    public interface View extends BaseView
    {

    }

    public interface Presenter extends BasePresenter<View>
    {

    }
}
