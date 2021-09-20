package com.jx.androiddemo;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 一般的Activity中要用到View操作是显示加载框、隐藏加载框、显示出错信息、显示当数据为空的时候的view之类
 */

public interface BaseView
{
    void showProgressDialog(int tag, boolean canCancel);

    void hiddenProgressDialog();

    void showMsg(String msg);

    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}
