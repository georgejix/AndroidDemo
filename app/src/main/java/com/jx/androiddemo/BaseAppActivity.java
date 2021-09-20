package com.jx.androiddemo;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.jx.androiddemo.event.NoticeEvent;
import com.jx.arch.BuildConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * <基础activity>
 */
public class BaseAppActivity extends SupportActivity implements LifecycleProvider<ActivityEvent>
{
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EventBus.getDefault().register(this);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NoticeEvent event)
    {
    }

    @Override
    @CallSuper
    protected void onResume()
    {
        super.onResume();
//        lifecycleSubject.onNext(ActivityEvent.RESUME);
        if (!BuildConfig.ON_OFF)
        {
        }
    }

    @Override
    @CallSuper
    protected void onPause()
    {
//        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        if (!BuildConfig.ON_OFF)
        {
        }
    }

    @Override
    @CallSuper
    protected void onDestroy()
    {
        BaseApplication.getInstance().delActivity(this);
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle()
    {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event)
    {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle()
    {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStart()
    {
        super.onStart();
//        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onStop()
    {
//        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }
}
