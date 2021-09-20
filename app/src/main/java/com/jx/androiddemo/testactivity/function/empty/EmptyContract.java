package com.jx.androiddemo.testactivity.function.empty;

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
