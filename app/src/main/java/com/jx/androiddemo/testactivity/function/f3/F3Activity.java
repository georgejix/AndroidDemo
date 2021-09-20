package com.jx.androiddemo.testactivity.function.f3;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F3Activity extends BaseMvpActivity<F3Presenter> implements F3Contract.View {
    private final String TAG = "TestHandlerActivity2";

    @BindView(R.id.framelayout)
    FrameLayout frameLayout;

    @BindView(R.id.tv_label1)
    TextView label1;

    @BindView(R.id.tv_label2)
    TextView label2;

    @BindView(R.id.tv_label3)
    TextView label3;

    @BindView(R.id.tv_label4)
    TextView label4;

    @BindView(R.id.textview_content)
    TextView contentText;

    private static final int REFRESH_VIEW = 1001;
    private FragmentManager fm;
    private TestHandlerFragment01 f1;
    private TestHandlerFragment02 f2;
    private ViewHandler viewHandler;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f3;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        testHandler();
        initView();
        initListener();
    }

    private void initView() {
        if (null == viewHandler) {
            viewHandler = new ViewHandler(this);
        }
        f1 = new TestHandlerFragment01();
        f2 = new TestHandlerFragment02();
        fm = getFragmentManager();
        fm.beginTransaction().add(R.id.framelayout, f1).add(R.id.framelayout, f2)
                .hide(f2).show(f1).commit();
        contentText.setText("before refreshView");
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(5000, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    viewHandler.sendEmptyMessage(REFRESH_VIEW);
                });

        //点击
        RxView.clicks(label1)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    fm.beginTransaction()
                            .hide(f2).show(f1).commit();
                });
        RxView.clicks(label2)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    fm.beginTransaction()
                            .hide(f1).show(f2).commit();
                });
        RxView.clicks(label3)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    handler1.sendEmptyMessage(1001);
                });
        RxView.clicks(label4)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    handler1.sendEmptyMessage(1002);
                });

    }

    private void refreshView() {
        contentText.setText("after refreshView");
        System.out.println("after refreshView");
    }

    private static class ViewHandler extends Handler {
        private final WeakReference<F3Activity> mainActivityReference;

        public ViewHandler(F3Activity activity) {
            mainActivityReference = new WeakReference<F3Activity>(
                    activity);
        }

        @Override
        public void handleMessage(Message msg) {
            F3Activity curActivity = mainActivityReference.get();
            if (curActivity != null) {
                switch (msg.what) {
                    case REFRESH_VIEW:
                        curActivity.refreshView();
                        break;
                }
            }
        }
    }

    Handler handler1;

    private void testHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, msg.what + "");
                        Looper.myLooper().quit();
                    }
                };
                Looper.loop();
                Log.d(TAG, "loop end");
            }
        }).start();
    }
}