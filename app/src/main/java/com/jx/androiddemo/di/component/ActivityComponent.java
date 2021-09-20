package com.jx.androiddemo.di.component;

import android.app.Activity;

import com.jx.androiddemo.testactivity.function.f1.F1Activity;
import com.jx.androiddemo.activity.main.LaunchActivity;
import com.jx.androiddemo.activity.main.MainActivity;
import com.jx.androiddemo.testactivity.ui.u1.U1Activity;
import com.jx.androiddemo.testactivity.ui.u2.U2Activity;
import com.jx.androiddemo.di.ActivityScope;
import com.jx.androiddemo.di.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(LaunchActivity launchActivity);

    void inject(F1Activity f1Activity);


    void inject(U1Activity u1Activity);

    void inject(U2Activity u2Activity);
}
