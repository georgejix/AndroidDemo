package com.jx.pullrefreshview.listener;


import androidx.annotation.NonNull;

import com.jx.pullrefreshview.api.RefreshLayout;

/**
 * 加载更多监听器
 * Created by WANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
