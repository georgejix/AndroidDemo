package com.jx.appfw.domain.repository;

import com.jx.appfw.domain.request.main.InitConfig;

import io.reactivex.Observable;

public interface MainRepository
{
    /**
     * 做一些初始化的工作
     *
     * @param initConfig 自定义变量
     */
    Observable<Boolean> initConfig(InitConfig initConfig);
}
