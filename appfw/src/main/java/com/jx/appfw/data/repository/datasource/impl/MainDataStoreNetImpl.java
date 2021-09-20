package com.jx.appfw.data.repository.datasource.impl;

import com.jx.appfw.data.db.MainDB;
import com.jx.appfw.data.net.MainApi;
import com.jx.appfw.data.repository.datasource.MainDataStore;
import com.jx.appfw.domain.request.main.InitConfig;

import io.reactivex.Observable;

/**
 * {@link MainDataStore} implementation for retrieving data from the network.
 */
public class MainDataStoreNetImpl implements MainDataStore
{
    private final MainApi mainApi;

    private final MainDB mainDB;

    public MainDataStoreNetImpl(MainApi mainApi, MainDB mainDB)
    {
        this.mainApi = mainApi;
        this.mainDB = mainDB;
    }

    @Override
    public Observable<Boolean> initConfig(InitConfig initConfig)
    {
        return null;
    }
}
