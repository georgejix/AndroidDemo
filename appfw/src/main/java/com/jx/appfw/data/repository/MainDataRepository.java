package com.jx.appfw.data.repository;

import com.jx.appfw.data.repository.datasource.MainDataStore;
import com.jx.appfw.data.repository.datasource.MainDataStoreFactory;
import com.jx.appfw.domain.repository.MainRepository;
import com.jx.appfw.domain.request.main.InitConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link MainRepository} for retrieving menu.
 */
@Singleton
public class MainDataRepository implements MainRepository
{
    private static String TAG = MainDataRepository.class.getName();

    private final MainDataStoreFactory mainDataStoreFactory;

    private final MainDataStore mainNetDataStore;

    private final MainDataStore mainDataStore;

    private final MainDataStore mainCacheDataStore;

    @Inject
    MainDataRepository(MainDataStoreFactory mainDataStoreFactory)
    {
        this.mainDataStoreFactory = mainDataStoreFactory;
        this.mainDataStore = mainDataStoreFactory.createMainDataStore();
        this.mainNetDataStore = mainDataStoreFactory.createNetMainDataStore();
        this.mainCacheDataStore = mainDataStoreFactory.createCacheMainDataStore();
    }

    @Override
    public Observable<Boolean> initConfig(InitConfig initConfig)
    {
        return this.mainCacheDataStore.initConfig(initConfig);
    }
}
