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
import com.jx.androiddemo.testactivity.function.f32.F32Activity;
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
import com.jx.androiddemo.testactivity.ui.u30.U30Activity;
import com.jx.androiddemo.testactivity.ui.u31.U31Activity;
import com.jx.androiddemo.testactivity.ui.u32.U32Activity;
import com.jx.androiddemo.testactivity.ui.u33.U33Activity;
import com.jx.androiddemo.testactivity.ui.u4.U4Activity;
import com.jx.androiddemo.testactivity.ui.u5.U5Activity;
import com.jx.androiddemo.testactivity.ui.u6.U6Activity;
import com.jx.androiddemo.testactivity.ui.u7.U7Activity;
import com.jx.androiddemo.testactivity.ui.u8.U8Activity;
import com.jx.androiddemo.testactivity.ui.u9.U9Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends BaseRxPresenter<MainContract.View> implements MainContract.Presenter {
    private final Context context;

    private List<MainPageBean> mLeftList = new ArrayList<>();
    private List<MainPageBean> mRightList = new ArrayList<>();


    @Inject
    MainPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void doDispose() {

    }

    @Override
    public List<MainPageBean> getLeftList() {
        if (0 == mLeftList.size()) {
            addLeft("简单线程,jni", F1Activity.class);
            addLeft("序列化和aidl", F2Activity.class);
            addLeft("handler", F3Activity.class);
            addLeft("相机", F4Activity.class);
            addLeft("CountDownLatch", F5Activity.class);
            addLeft("nsd", F6Activity.class);
            addLeft("net", F7Activity.class);
            addLeft("xml", F8Activity.class);
            addLeft("intentService", F9Activity.class);
            addLeft("Thumbnail", F10Activity.class);
            addLeft("DeviceAwake", F11Activity.class);
            addLeft("Http", F12Activity.class);
            addLeft("thread", F13Activity.class);
            addLeft("timer", F14Activity.class);
            addLeft("notification", F15Activity.class);
            addLeft("sqlite", F16Activity.class);
            addLeft("contentProvider", F17Activity.class);
            addLeft("service", F18Activity.class);
            addLeft("自定义handler", F19Activity.class);
            addLeft("sharedPreference", F20Activity.class);
            addLeft("mediaPlayer", F21Activity.class);
            addLeft("显示yuv图片", F22Activity.class);
            addLeft("mp4toYuv", F23Activity.class);
            addLeft("surfaceView", F24Activity.class);
            addLeft("videoView", F25Activity.class);
            addLeft("observer", F26Activity.class);
            addLeft("系统相册选择图片", F27Activity.class);
            addLeft("系统相册选择视频，带ui", F28Activity.class);
            addLeft("sdk提取网络视频中音频", F29Activity.class);
            addLeft("ffmpeg提取网络视频中音频", F30Activity.class);
            addLeft("loading", F31Activity.class);
            addLeft("音频播放", F32Activity.class);
        }

        Collections.reverse(mLeftList);
        return mLeftList;
    }

    @Override
    public List<MainPageBean> getRightList() {
        if (0 == mRightList.size()) {
            addRight("全屏切换，子线程addView", U1Activity.class);
            addRight("上拉加载，下拉刷新", U2Activity.class);
            addRight("activity生命周期,popupWindow", U3Activity.class);
            addRight("bitmap加载显示", U4Activity.class);
            addRight("openglES", U5Activity.class);
            addRight("animation", U6Activity.class);
            addRight("圆角图片", U7Activity.class);
            addRight("TouchEvent,popupWindow", U8Activity.class);
            addRight("VelocityTracker", U9Activity.class);
            addRight("input显示隐藏密码", U10Activity.class);
            addRight("TransparentActivity", U11Activity.class);
            addRight("dialogActivity", U12Activity.class);
            addRight("CheckBox", U13Activity.class);
            addRight("TestSomeView", U14Activity.class);
            addRight("imageView", U15Activity.class);
            addRight("相机选择照片，拍照", U16Activity.class);
            addRight("选择日期", U17Activity.class);
            addRight("选择日期2", U18Activity.class);
            addRight("Bezier曲线", U19Activity.class);
            addRight("梯形", U20Activity.class);
            addRight("事件分发", U21Activity.class);
            addRight("loading", U22Activity.class);
            addRight("一行两列linearlayout", U23Activity.class);
            addRight("ConstraintLayout", U24Activity.class);
            addRight("循环图片轮播", U25Activity.class);
            addRight("封装循环图片轮播", U26Activity.class);
            addRight("悬浮窗口", U27Activity.class);
            addRight("弹框提示", U28Activity.class);
            addRight("可拖动悬浮窗口", U29Activity.class);
            addRight("loading", U30Activity.class);
            addRight("共享动画", U31Activity.class);
            addRight("viewpager2", U32Activity.class);
            addRight("清除图片", U33Activity.class);
        }

        Collections.reverse(mRightList);
        return mRightList;
    }

    private void addLeft(String name, Class clazz) {
        mLeftList.add(new MainPageBean(name, clazz));
    }

    private void addRight(String name, Class clazz) {
        mRightList.add(new MainPageBean(name, clazz));
    }
}
