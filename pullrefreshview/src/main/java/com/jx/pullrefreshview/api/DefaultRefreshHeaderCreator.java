package com.jx.pullrefreshview.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 默认Header创建器
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    com.jx.pullrefreshview.api.RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull com.jx.pullrefreshview.api.RefreshLayout layout);
}
