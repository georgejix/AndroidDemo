package com.jx.appfw.data.repository.datasource.impl;

import android.content.Context;

abstract class BaseDataStore
{

    final Context mContext;

    BaseDataStore(Context context)
    {
        this.mContext = context;
    }
}
