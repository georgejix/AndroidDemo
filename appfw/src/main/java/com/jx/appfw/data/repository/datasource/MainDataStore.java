package com.jx.appfw.data.repository.datasource;

import com.jx.appfw.domain.request.main.InitConfig;

import io.reactivex.Observable;

/**
 * LoginDataStore for retrieving data from the network.
 */
public interface MainDataStore
{
    /**
     * 做一些初始化的工作
     *
     * @param initConfig 自定义变量
     */
    Observable<Boolean> initConfig(InitConfig initConfig);
}
