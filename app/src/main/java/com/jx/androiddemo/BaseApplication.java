package com.jx.androiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.fragment.app.DialogFragment;
import androidx.multidex.MultiDexApplication;

import com.jx.arch.util.AppProcessUtil;
import com.jx.arch.util.ArchTool;
import com.jx.arch.util.QMLog;
import com.jx.androiddemo.constant.CashInit;
import com.jx.androiddemo.constant.NotiTag;
import com.jx.androiddemo.di.component.AppComponent;
import com.jx.androiddemo.di.component.DaggerAppComponent;
import com.jx.androiddemo.di.module.AppModule;
import com.jx.androiddemo.event.NoticeEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import javax.inject.Inject;

/**
 * <baseapplication>
 *
 * @author wangtao
 */
public class BaseApplication extends MultiDexApplication {
    /**
     * app实例
     */
    private static BaseApplication sInstance;

    private static Handler mHandler;

    /**
     * 记录Activity的总个数
     */
    public int count = 0;

    @Inject
    CashInit cashInit;


    @Override
    public void onCreate() {
        super.onCreate();
        QMLog.i("BaseApplication", "onCreate" + AppProcessUtil.isAppProcess(this));
        if (AppProcessUtil.isAppProcess(this)) {
            sInstance = this;
            ArchTool.init(this); // 初始化
            getAppComponent().inject(this);
            mHandler = new Handler();
            registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
            initDir();
        }
    }

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        cashInit.exitApplication();
    }

    /**
     * 初始化App的Component
     *
     * @return
     */
    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(sInstance))
                .build();
    }

    private class SwitchBackgroundCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            cashInit.addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (count == 0) { //后台切换到前台
                QMLog.i("info", ">>>>>>>>>>>>>>>>>>>App切到前台");
                EventBus.getDefault().post(new NoticeEvent<>(NotiTag.TAG_SHOW_FONT));
            }
            count++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            count--;
            if (count == 0) { //前台切换到后台
                QMLog.i("info", ">>>>>>>>>>>>>>>>>>>App切到后台");
                EventBus.getDefault().post(new NoticeEvent<>(NotiTag.TAG_HIDDEN_BACKGROUND));
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            cashInit.deleteActivity(activity);
        }
    }

    public Activity getCurrentActivity() {
        return cashInit.getCurActivity();
    }

    public void startToLoginActivity() {
        cashInit.startToLoginActivity();
    }

    public void delActivity(Activity activity) {
        cashInit.deleteActivity(activity);
    }

    public void addDialogFragment(DialogFragment dialogFragment) {
        cashInit.addDialogFragment(dialogFragment);
    }

    public void delDialogFragment(DialogFragment dialogFragment) {
        cashInit.removeDialogFragment(dialogFragment);
    }

    public void dismissAllDialogFragment() {
        cashInit.dismissAllDialogFragment();
    }

    public DialogFragment getCurDialogFragment() {
        return cashInit.getCurDialogFragment();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    private static String path;
    private static File projectFile;

    public static void initDir(){
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demoproject";
        if(null != path){
            projectFile = new File(path);
            if(null != projectFile){
                if(!projectFile.exists()){
                    projectFile.mkdirs();
                }
            }
        }
    }

    public static String getFile() {
        return null == path ? Environment.getExternalStorageDirectory().getAbsolutePath() : path;
    }
}
