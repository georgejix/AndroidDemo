package com.jx.androiddemo.di.component;

import android.app.Activity;

import com.jx.androiddemo.activity.main.LaunchActivity;
import com.jx.androiddemo.activity.main.MainActivity;
import com.jx.androiddemo.di.ActivityScope;
import com.jx.androiddemo.di.module.ActivityModule;
import com.jx.androiddemo.testactivity.empty.EmptyActivity;
import com.jx.androiddemo.testactivity.function.f1to10.f1.F1Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f10.F10Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f11.F11Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f12.F12Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f13.F13Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f14.F14Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f15.F15Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f16.F16Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f17.F17Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f18.F18Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f19.F19Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f2.F2Activity;
import com.jx.androiddemo.testactivity.function.f11to20.f20.F20Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f21.F21Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f22.F22Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f23.F23Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f24.F24Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f25.F25Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f26.F26Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f27.F27Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f28.F28Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f29.F29Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f3.F3Activity;
import com.jx.androiddemo.testactivity.function.f21to30.f30.F30Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f31.F31Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f32.F32Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f4.Camera2Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f4.Camera3Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f4.CameraActivity;
import com.jx.androiddemo.testactivity.function.f1to10.f4.F4Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f5.F5Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f6.F6Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f7.F7Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f8.F8Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f9.F9Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f33.F33Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f34.F34Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f35.F35Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f36.F36Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f37.F37Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f38.F38Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f39.F39Activity;
import com.jx.androiddemo.testactivity.function.f31to40.f40.F40Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f41.F41Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f42.F42Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f43.F43Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f44.F44Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f45.F45Activity;
import com.jx.androiddemo.testactivity.function.f41to50.f46.F46Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui1.U1Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui10.U10Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui11.U11Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui12.U12Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui13.U13Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui14.U14Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui15.U15Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui16.U16Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui17.U17Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui18.U18Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui19.U19Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui2.U2Activity;
import com.jx.androiddemo.testactivity.ui.ui11to20.ui20.U20Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui21.U21Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui22.U22Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui23.U23Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui24.U24Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui25.U25Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui26.U26Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui27.U27Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui28.U28Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui29.U29Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui3.U3Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui3.U3_1Activity;
import com.jx.androiddemo.testactivity.ui.ui21to30.ui30.U30Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui37.U37Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui31.U31Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui32.U32Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui33.U33Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui34.U34Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui35.U35Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui4.U4Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui5.U5Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui6.U6Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui7.U7Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui8.U8Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui9.U9Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui36.U36Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui38.U38Activity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{

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

    void inject(F32Activity f32Activity);

    void inject(F33Activity f33Activity);

    void inject(F34Activity f34Activity);

    void inject(F35Activity f35Activity);

    void inject(F36Activity f36Activity);

    void inject(F37Activity f37Activity);

    void inject(F38Activity f38Activity);

    void inject(F39Activity f39Activity);

    void inject(F40Activity f40Activity);

    void inject(F41Activity f41Activity);

    void inject(F42Activity f42Activity);

    void inject(F43Activity f43Activity);

    void inject(F44Activity f44Activity);

    void inject(F45Activity f45Activity);

    void inject(F46Activity f46Activity);

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

    void inject(U31Activity u31Activity);

    void inject(U32Activity u32Activity);

    void inject(U33Activity u33Activity);

    void inject(U34Activity u34Activity);

    void inject(U35Activity u35Activity);

    void inject(U36Activity u36Activity);

    void inject(U37Activity u37Activity);

    void inject(U38Activity u38Activity);
}
