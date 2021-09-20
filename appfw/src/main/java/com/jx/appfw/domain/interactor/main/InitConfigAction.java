package com.jx.appfw.domain.interactor.main;

import com.jx.appfw.domain.repository.MainRepository;
import com.jx.appfw.domain.request.main.InitConfig;
import com.jx.arch.domain.executor.PostExecutionThread;
import com.jx.arch.domain.executor.ThreadExecutor;
import com.jx.arch.domain.interactor.UseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InitConfigAction extends UseCase<Boolean, InitConfig>
{

    private final MainRepository repository;

    @Inject
    InitConfigAction(MainRepository repository,
                     ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread)
    {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(InitConfig initConfig)
    {
        return repository.initConfig(initConfig);
    }
}
