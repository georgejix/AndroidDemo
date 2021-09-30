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
import com.jx.androiddemo.testactivity.function.f3.F3Activity;
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
    private static final String TAG = "MainPresenter";

    private Context context;

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
            mMainPageList.add(new MainPageBean("简单线程", F1Activity.class));
            mMainPageList.add(new MainPageBean("序列化和aidl", F2Activity.class));
            mMainPageList.add(new MainPageBean("handler", F3Activity.class));
            mMainPageList.add(new MainPageBean("相机", F4Activity.class));
            mMainPageList.add(new MainPageBean("CountDownLatch", F5Activity.class));
            mMainPageList.add(new MainPageBean("nsd", F6Activity.class));
            mMainPageList.add(new MainPageBean("net", F7Activity.class));
            mMainPageList.add(new MainPageBean("xml", F8Activity.class));
            mMainPageList.add(new MainPageBean("intentService", F9Activity.class));
            mMainPageList.add(new MainPageBean("Thumbnail", F10Activity.class));
            mMainPageList.add(new MainPageBean("DeviceAwake", F11Activity.class));
            mMainPageList.add(new MainPageBean("Http", F12Activity.class));
            mMainPageList.add(new MainPageBean("thread", F13Activity.class));
            mMainPageList.add(new MainPageBean("timer", F14Activity.class));
            mMainPageList.add(new MainPageBean("notification", F15Activity.class));
            mMainPageList.add(new MainPageBean("sqlite", F16Activity.class));
            mMainPageList.add(new MainPageBean("contentProvider", F17Activity.class));
            mMainPageList.add(new MainPageBean("service", F18Activity.class));
            mMainPageList.add(new MainPageBean("自定义handler", F19Activity.class));
            mMainPageList.add(new MainPageBean("sharedpreference", F20Activity.class));
            mMainPageList.add(new MainPageBean("mediaplayer", F21Activity.class));
            mMainPageList.add(new MainPageBean("显示yuv图片", F22Activity.class));
            mMainPageList.add(new MainPageBean("mp4toyuv", F23Activity.class));


            mMainPageList.add(new MainPageBean("---------------------------------", null));


            mMainPageList.add(new MainPageBean("简单ui", U1Activity.class));
            mMainPageList.add(new MainPageBean("上拉加载，下拉刷新", U2Activity.class));
            mMainPageList.add(new MainPageBean("activity生命周期,popupwindow", U3Activity.class));
            mMainPageList.add(new MainPageBean("bitmap加载显示", U4Activity.class));
            mMainPageList.add(new MainPageBean("opengles", U5Activity.class));
            mMainPageList.add(new MainPageBean("animation", U6Activity.class));
            mMainPageList.add(new MainPageBean("圆角图片", U7Activity.class));
            mMainPageList.add(new MainPageBean("TouchEvent,popupwindow", U8Activity.class));
            mMainPageList.add(new MainPageBean("VelocityTracker", U9Activity.class));
            mMainPageList.add(new MainPageBean("input显示隐藏密码", U10Activity.class));
            mMainPageList.add(new MainPageBean("TransparentActivity", U11Activity.class));
            mMainPageList.add(new MainPageBean("dialogActivity", U12Activity.class));
            mMainPageList.add(new MainPageBean("CheckBox", U13Activity.class));
            mMainPageList.add(new MainPageBean("TestSomeView", U14Activity.class));
            mMainPageList.add(new MainPageBean("imageView", U15Activity.class));
            mMainPageList.add(new MainPageBean("相机选择照片，拍照", U16Activity.class));
            mMainPageList.add(new MainPageBean("选择日期", U17Activity.class));
            mMainPageList.add(new MainPageBean("选择日期2", U18Activity.class));
            mMainPageList.add(new MainPageBean("Bezier曲线", U19Activity.class));
            mMainPageList.add(new MainPageBean("梯形", U20Activity.class));
            mMainPageList.add(new MainPageBean("事件分发", U21Activity.class));
            mMainPageList.add(new MainPageBean("loading", U22Activity.class));


        }
        return mMainPageList;
    }
}
