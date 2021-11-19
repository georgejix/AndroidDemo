package com.jx.androiddemo.presenter.main;

import android.content.Context;

import com.jx.androiddemo.bean.main.MainPageBean;
import com.jx.androiddemo.contract.main.MainContract;
import com.jx.androiddemo.presenter.BaseRxPresenter;
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
import com.jx.androiddemo.testactivity.ui.u3.U3Activity;
import com.jx.androiddemo.testactivity.ui.u4.U4Activity;
import com.jx.androiddemo.testactivity.ui.u5.U5Activity;
import com.jx.androiddemo.testactivity.ui.u6.U6Activity;
import com.jx.androiddemo.testactivity.ui.u7.U7Activity;
import com.jx.androiddemo.testactivity.ui.u8.U8Activity;
import com.jx.androiddemo.testactivity.ui.u9.U9Activity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends BaseRxPresenter<MainContract.View> implements MainContract.Presenter {
    private final Context context;

    private List<MainPageBean> mMainPageList;


    @Inject
    MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

    @Override
    public List<MainPageBean> getMainPageList() {
        if (null == mMainPageList) {
            mMainPageList = new ArrayList<>();
            add("简单线程,jni", F1Activity.class);
            add("序列化和aidl", F2Activity.class);
            add("handler", F3Activity.class);
            add("相机", F4Activity.class);
            add("CountDownLatch", F5Activity.class);
            add("nsd", F6Activity.class);
            add("net", F7Activity.class);
            add("xml", F8Activity.class);
            add("intentService", F9Activity.class);
            add("Thumbnail", F10Activity.class);
            add("DeviceAwake", F11Activity.class);
            add("Http", F12Activity.class);
            add("thread", F13Activity.class);
            add("timer", F14Activity.class);
            add("notification", F15Activity.class);
            add("sqlite", F16Activity.class);
            add("contentProvider", F17Activity.class);
            add("service", F18Activity.class);
            add("自定义handler", F19Activity.class);
            add("sharedPreference", F20Activity.class);
            add("mediaPlayer", F21Activity.class);
            add("显示yuv图片", F22Activity.class);
            add("mp4toYuv", F23Activity.class);
            add("surfaceView", F24Activity.class);
            add("videoView", F25Activity.class);
            add("observer", F26Activity.class);
            add("系统相册选择图片", F27Activity.class);
            add("系统相册选择视频，带ui", F28Activity.class);
            add("sdk提取网络视频中音频", F29Activity.class);
            add("ffmpeg提取网络视频中音频", F30Activity.class);
            add("选择本地音频", F31Activity.class);


            add("---------------------------------", null);


            add("全屏切换，子线程addView", U1Activity.class);
            add("上拉加载，下拉刷新", U2Activity.class);
            add("activity生命周期,popupWindow", U3Activity.class);
            add("bitmap加载显示", U4Activity.class);
            add("openglES", U5Activity.class);
            add("animation", U6Activity.class);
            add("圆角图片", U7Activity.class);
            add("TouchEvent,popupWindow", U8Activity.class);
            add("VelocityTracker", U9Activity.class);
            add("input显示隐藏密码", U10Activity.class);
            add("TransparentActivity", U11Activity.class);
            add("dialogActivity", U12Activity.class);
            add("CheckBox", U13Activity.class);
            add("TestSomeView", U14Activity.class);
            add("imageView", U15Activity.class);
            add("相机选择照片，拍照", U16Activity.class);
            add("选择日期", U17Activity.class);
            add("选择日期2", U18Activity.class);
            add("Bezier曲线", U19Activity.class);
            add("梯形", U20Activity.class);
            add("事件分发", U21Activity.class);
            add("loading", U22Activity.class);
            add("一行两列linearlayout", U23Activity.class);
            add("ConstraintLayout", U24Activity.class);
            add("循环图片轮播", U25Activity.class);
            add("封装循环图片轮播", U26Activity.class);
            add("悬浮窗口", U27Activity.class);


        }
        return mMainPageList;
    }

    private void add(String name, Class clazz) {
        if (null == mMainPageList) {
            return;
        }
        mMainPageList.add(new MainPageBean(name, clazz));
    }
}
