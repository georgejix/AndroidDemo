package com.jx.androiddemo.presenter.main;

import android.content.Context;

import com.jx.androiddemo.bean.main.MainPageBean;
import com.jx.androiddemo.contract.main.MainContract;
import com.jx.androiddemo.presenter.BaseRxPresenter;
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
import com.jx.androiddemo.testactivity.function.f1to10.f4.F4Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f5.F5Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f6.F6Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f7.F7Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f8.F8Activity;
import com.jx.androiddemo.testactivity.function.f1to10.f9.F9Activity;
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
import com.jx.androiddemo.testactivity.ui.ui21to30.ui30.U30Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui31.U31Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui32.U32Activity;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui33.U33Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui4.U4Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui5.U5Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui6.U6Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui7.U7Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui8.U8Activity;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui9.U9Activity;

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
