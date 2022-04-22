package com.jx.androiddemo.testactivity.function.f21to30.f26;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class F26Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final static String TAG = "ObserverActivity";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f26;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        test1();
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

        //点击
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    private void test1() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        Observer<String> observer2 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "2_" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "2_onComplete");
            }
        };
        Observable<String> observable = Observable.create((ObservableOnSubscribe<String>) e -> {
            e.onNext("next1");
            e.onNext("next2");
            e.onComplete();
        });
        observable.subscribe(observer);
        observable
                .subscribeOn(Schedulers.io())    // 给上面代码分配异步线程
                .observeOn(AndroidSchedulers.mainThread()) // 给下面代码分配主线程;
                .subscribe(observer2);
    }
}