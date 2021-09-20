package com.jx.androiddemo.di.module;

import android.content.Context;

import com.jx.appfw.data.repository.MainDataRepository;
import com.jx.appfw.domain.repository.MainRepository;
import com.jx.arch.data.executor.JobExecutor;
import com.jx.arch.domain.executor.PostExecutionThread;
import com.jx.arch.domain.executor.ThreadExecutor;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private final BaseApplication application;

    public AppModule(BaseApplication application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    BaseApplication provideBaseApplication()
    {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext()
    {
        return this.application.getApplicationContext();
    }


    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor)
    {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread)
    {
        return uiThread;
    }

    @Provides
    @Singleton
    MainRepository provideMainRepository(MainDataRepository mainDataRepository)
    {
        return mainDataRepository;
    }
}
