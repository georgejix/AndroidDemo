package com.jx.androiddemo.testactivity.function.f11to20.f11;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F11Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "DeviceAwakeActivity";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f11;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        //保持屏幕长亮
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //持续唤醒cpu
        /*PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();
        wakeLock.release();*/

        //某个固定时间，发送一条广播（一次性或间断性发送）
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        //calendar.set(Calendar.HOUR_OF_DAY, 15);
        //calendar.set(Calendar.MINUTE, 40);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), alarmIntent);
        Log.d(TAG, "start alarmmanager");
        //重复闹钟
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //        1000 * 60 * 20, alarmIntent);
        //取消闹钟
        // If the alarm has been set, cancel it.
        /*if (alarmManager!= null) {
            alarmManager.cancel(alarmIntent);
        }*/

        //修改receiver在配置文件中android:enabled="false"属性
        /*ComponentName receiver = new ComponentName(getApplicationContext(), SampleBootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Intent service = new Intent(mContext, MyIntentService.class);
                    startService(service);
                });

        //点击
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }
}