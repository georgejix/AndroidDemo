package com.jx.androiddemo.di.component;

import android.app.Activity;

import com.jx.androiddemo.testactivity.function.empty.EmptyActivity;
import com.jx.androiddemo.testactivity.function.f1.F1Activity;
import com.jx.androiddemo.activity.main.LaunchActivity;
import com.jx.androiddemo.activity.main.MainActivity;
import com.jx.androiddemo.testactivity.function.f2.F2Activity;
import com.jx.androiddemo.testactivity.function.f3.F3Activity;
import com.jx.androiddemo.testactivity.function.f4.Camera2Activity;
import com.jx.androiddemo.testactivity.function.f4.Camera3Activity;
import com.jx.androiddemo.testactivity.function.f4.CameraActivity;
import com.jx.androiddemo.testactivity.function.f4.F4Activity;
import com.jx.androiddemo.testactivity.ui.u1.U1Activity;
import com.jx.androiddemo.testactivity.ui.u2.U2Activity;
import com.jx.androiddemo.di.ActivityScope;
import com.jx.androiddemo.di.module.ActivityModule;
import com.jx.androiddemo.testactivity.ui.u3.U3Activity;
import com.jx.androiddemo.testactivity.ui.u3.U3_1Activity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(LaunchActivity launchActivity);

    void inject(EmptyActivity emptyActivity);

    void inject(F1Activity f1Activity);

    void inject(F2Activity f2Activity);

    void inject(F3Activity f3Activity);

    void inject(F4Activity f4Activity);
    void inject(CameraActivity cameraActivity);
    void inject(Camera2Activity camera2Activity);
    void inject(Camera3Activity camera3Activity);


    void inject(U1Activity u1Activity);

    void inject(U2Activity u2Activity);

    void inject(U3Activity u3Activity);

    void inject(U3_1Activity u3_1Activity);
}
