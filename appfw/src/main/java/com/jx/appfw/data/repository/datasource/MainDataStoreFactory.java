package com.jx.appfw.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jx.appfw.data.db.MainDB;
import com.jx.appfw.data.db.MainDBImpl;
import com.jx.appfw.data.mapper.MainDataMapper;
import com.jx.appfw.data.net.MainApi;
import com.jx.appfw.data.net.MainApiImpl;
import com.jx.appfw.data.repository.datasource.impl.MainDataStoreCacheImpl;
import com.jx.appfw.data.repository.datasource.impl.MainDataStoreNetImpl;
import com.jx.arch.util.DeviceUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link MainDataStoreFactory}.
 */
@Singleton
public class MainDataStoreFactory
{
    private final Context context;

    private final MainDB mainDB;

    private final MainDataMapper mainDataMapper;

    @Inject
    MainDataStoreFactory(@NonNull Context context, MainDataMapper mainDataMapper)
    {
        this.context = context.getApplicationContext();
        mainDB = new MainDBImpl();
        this.mainDataMapper = mainDataMapper;
    }

    public MainDataStore createMainDataStore()
    {
        MainDataStore mainDataStore;
        if (DeviceUtils.isThereInternetConnection())
        {
            mainDataStore = createNetMainDataStore();
        }
        else
        {
            mainDataStore = createCacheMainDataStore();
        }
        return mainDataStore;
    }

    public MainDataStore createCacheMainDataStore()
    {
        return new MainDataStoreCacheImpl(this.mainDB, this.mainDataMapper);
    }

    public MainDataStore createNetMainDataStore()
    {
        final MainApi mainApi = new MainApiImpl();
        return new MainDataStoreNetImpl(mainApi, this.mainDB);
    }
}
