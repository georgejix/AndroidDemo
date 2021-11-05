package com.jx.androiddemo.di.component;

import android.app.Activity;

import com.jx.androiddemo.activity.main.LaunchActivity;
import com.jx.androiddemo.activity.main.MainActivity;
import com.jx.androiddemo.di.ActivityScope;
import com.jx.androiddemo.di.module.ActivityModule;
import com.jx.androiddemo.testactivity.function.empty.EmptyActivity;
import com.jx.androiddemo.testactivity.function.f1.F1Activity;
import com.jx.androiddemo.testactivity.function.f10.F10Activity;
import com.jx.androiddemo.testactivity.function.f11.F11Activity;
import com.jx.androiddemo.testactivity.function.f12.F12Activity;
import com.jx.androiddemo.testactivity.function.f13.F13Activity;
import com.jx.androiddemo.testactivity.function.f14.F14Activity;
import com.jx.androiddemo.testactivity.function.f15.F15Activity;
import com.jx.androiddemo.testactivity.function.f16.F16Activity;
import com.jx.androiddemo.testactivity.function.f17.F17Activity;
import com.jx.androiddemo.testactivity.function.f18.F18Activity;
import com.jx.androiddemo.testactivity.function.f19.F19Activity;
import com.jx.androiddemo.testactivity.function.f2.F2Activity;
import com.jx.androiddemo.testactivity.function.f20.F20Activity;
import com.jx.androiddemo.testactivity.function.f21.F21Activity;
import com.jx.androiddemo.testactivity.function.f22.F22Activity;
import com.jx.androiddemo.testactivity.function.f23.F23Activity;
import com.jx.androiddemo.testactivity.function.f24.F24Activity;
import com.jx.androiddemo.testactivity.function.f25.F25Activity;
import com.jx.androiddemo.testactivity.function.f26.F26Activity;
import com.jx.androiddemo.testactivity.function.f27.F27Activity;
import com.jx.androiddemo.testactivity.function.f28.F28Activity;
import com.jx.androiddemo.testactivity.function.f29.F29Activity;
import com.jx.androiddemo.testactivity.function.f3.F3Activity;
import com.jx.androiddemo.testactivity.function.f30.F30Activity;
import com.jx.androiddemo.testactivity.function.f31.F31Activity;
import com.jx.androiddemo.testactivity.function.f4.Camera2Activity;
import com.jx.androiddemo.testactivity.function.f4.Camera3Activity;
import com.jx.androiddemo.testactivity.function.f4.CameraActivity;
import com.jx.androiddemo.testactivity.function.f4.F4Activity;
import com.jx.androiddemo.testactivity.function.f5.F5Activity;
import com.jx.androiddemo.testactivity.function.f6.F6Activity;
import com.jx.androiddemo.testactivity.function.f7.F7Activity;
import com.jx.androiddemo.testactivity.function.f8.F8Activity;
import com.jx.androiddemo.testactivity.function.f9.F9Activity;
import com.jx.androiddemo.testactivity.ui.u1.U1Activity;
import com.jx.androiddemo.testactivity.ui.u10.U10Activity;
import com.jx.androiddemo.testactivity.ui.u11.U11Activity;
import com.jx.androiddemo.testactivity.ui.u12.U12Activity;
import com.jx.androiddemo.testactivity.ui.u13.U13Activity;
import com.jx.androiddemo.testactivity.ui.u14.U14Activity;
import com.jx.androiddemo.testactivity.ui.u15.U15Activity;
import com.jx.androiddemo.testactivity.ui.u16.U16Activity;
import com.jx.androiddemo.testactivity.ui.u17.U17Activity;
import com.jx.androiddemo.testactivity.ui.u18.U18Activity;
import com.jx.androiddemo.testactivity.ui.u19.U19Activity;
import com.jx.androiddemo.testactivity.ui.u2.U2Activity;
import com.jx.androiddemo.testactivity.ui.u20.U20Activity;
import com.jx.androiddemo.testactivity.ui.u21.U21Activity;
import com.jx.androiddemo.testactivity.ui.u22.U22Activity;
import com.jx.androiddemo.testactivity.ui.u23.U23Activity;
import com.jx.androiddemo.testactivity.ui.u24.U24Activity;
import com.jx.androiddemo.testactivity.ui.u25.U25Activity;
import com.jx.androiddemo.testactivity.ui.u26.U26Activity;
import com.jx.androiddemo.testactivity.ui.u27.U27Activity;
import com.jx.androiddemo.testactivity.ui.u28.U28Activity;
import com.jx.androiddemo.testactivity.ui.u29.U29Activity;
import com.jx.androiddemo.testactivity.ui.u3.U3Activity;
import com.jx.androiddemo.testactivity.ui.u3.U3_1Activity;
import com.jx.androiddemo.testactivity.ui.u30.U30Activity;
import com.jx.androiddemo.testactivity.ui.u4.U4Activity;
import com.jx.androiddemo.testactivity.ui.u5.U5Activity;
import com.jx.androiddemo.testactivity.ui.u6.U6Activity;
import com.jx.androiddemo.testactivity.ui.u7.U7Activity;
import com.jx.androiddemo.testactivity.ui.u8.U8Activity;
import com.jx.androiddemo.testactivity.ui.u9.U9Activity;

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

    void inject(F5Activity f5Activity);

    void inject(F6Activity f6Activity);

    void inject(F7Activity f7Activity);

    void inject(F8Activity f8Activity);

    void inject(F9Activity f9Activity);

    void inject(F10Activity f10Activity);

    void inject(F11Activity f11Activity);

    void inject(F12Activity f12Activity);

    void inject(F13Activity f13Activity);

    void inject(F14Activity f14Activity);

    void inject(F15Activity f15Activity);

    void inject(F16Activity f16Activity);

    void inject(F17Activity f17Activity);

    void inject(F18Activity f18Activity);

    void inject(F19Activity f19Activity);

    void inject(F20Activity f20Activity);

    void inject(F21Activity f21Activity);

    void inject(F22Activity f22Activity);

    void inject(F23Activity f23Activity);

    void inject(F24Activity f24Activity);

    void inject(F25Activity f25Activity);

    void inject(F26Activity f26Activity);

    void inject(F27Activity f27Activity);

    void inject(F28Activity f28Activity);

    void inject(F29Activity f29Activity);

    void inject(F30Activity f30Activity);

    void inject(F31Activity f31Activity);

    void inject(U1Activity u1Activity);

    void inject(U2Activity u2Activity);

    void inject(U3Activity u3Activity);

    void inject(U3_1Activity u3_1Activity);

    void inject(U4Activity u4Activity);

    void inject(U5Activity u5Activity);

    void inject(U6Activity u6Activity);

    void inject(U7Activity u7Activity);

    void inject(U8Activity u8Activity);

    void inject(U9Activity u9Activity);

    void inject(U10Activity u10Activity);

    void inject(U11Activity u11Activity);

    void inject(U12Activity u12Activity);

    void inject(U13Activity u13Activity);

    void inject(U14Activity u14Activity);

    void inject(U15Activity u15Activity);

    void inject(U16Activity u16Activity);

    void inject(U17Activity u17Activity);

    void inject(U18Activity u18Activity);

    void inject(U19Activity u19Activity);

    void inject(U20Activity u20Activity);

    void inject(U21Activity u21Activity);

    void inject(U22Activity u22Activity);

    void inject(U23Activity u23Activity);

    void inject(U24Activity u24Activity);

    void inject(U25Activity u25Activity);

    void inject(U26Activity u26Activity);

    void inject(U27Activity u27Activity);

    void inject(U28Activity u28Activity);

    void inject(U29Activity u29Activity);

    void inject(U30Activity u30Activity);
}
