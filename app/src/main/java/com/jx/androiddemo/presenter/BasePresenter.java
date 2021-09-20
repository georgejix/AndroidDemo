package com.jx.androiddemo.presenter;

import com.jx.androiddemo.BaseView;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface BasePresenter<T extends BaseView>
{
    void attachView(T view);

    void detachView();
}
