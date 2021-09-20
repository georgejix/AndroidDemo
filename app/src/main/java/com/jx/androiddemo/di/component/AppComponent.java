package com.jx.androiddemo.di.component;

import android.content.Context;

import com.jx.appfw.domain.repository.MainRepository;
import com.jx.arch.domain.executor.PostExecutionThread;
import com.jx.arch.domain.executor.ThreadExecutor;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{
    void inject(BaseApplication application);

    BaseApplication baseApplication();  // 提供App的Context

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    /*********************************************************************************
     * 注册业务中心
     *********************************************************************************/

    MainRepository mainRepository();

}
