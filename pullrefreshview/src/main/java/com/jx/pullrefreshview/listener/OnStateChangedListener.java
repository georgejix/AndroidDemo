package com.jx.pullrefreshview.listener;
import androidx.annotation.NonNull;

import com.jx.pullrefreshview.api.RefreshLayout;
import com.jx.pullrefreshview.constant.RefreshState;

/**
 * 刷新状态改变监听器
 * Created by WANG on 2017/5/26.
 */

public interface OnStateChangedListener {
    /**
     * 状态改变事件 {@link RefreshState}
     * @param refreshLayout RefreshLayout
     * @param oldState 改变之前的状态
     * @param newState 改变之后的状态
     */
    void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState);
}
