package com.jx.androiddemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jx.arch.BuildConfig;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.jx.androiddemo.event.NoticeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * <基础Fragment>
 * <功能详细描述>
 */
public class BaseAppFragment extends SupportFragment implements LifecycleProvider<FragmentEvent>
{
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NoticeEvent event)
    {
    }

    @Override
    @CallSuper
    public void onDestroy()
    {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle()
    {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event)
    {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle()
    {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity)
    {
        super.onAttach(activity);
//        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
//        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart()
    {
        super.onStart();
//        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume()
    {
        super.onResume();
//        lifecycleSubject.onNext(FragmentEvent.RESUME);
        if (!BuildConfig.ON_OFF)
        {
        }
    }

    @Override
    @CallSuper
    public void onPause()
    {
//        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        if (!BuildConfig.ON_OFF)
        {
        }
    }

    @Override
    @CallSuper
    public void onStop()
    {
//        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    public void onDestroyView()
    {
//        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    @CallSuper
    public void onDetach()
    {
//        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }
}
