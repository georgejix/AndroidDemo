package com.jx.pullrefreshview.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 默认Footer创建器
 */

public interface DefaultRefreshFooterCreator {
    @NonNull
    com.jx.pullrefreshview.api.RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull com.jx.pullrefreshview.api.RefreshLayout layout);
}
