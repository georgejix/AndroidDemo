package com.jx.androiddemo.testactivity.ui.u27;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.androiddemo.tool.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U27Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_show_hide_pop_view)
    TextView mShowHidePopView;

    private Messenger mSendMessenger;
    private Messenger mReplyMessenger;
    private ServiceConnection mPopConnection;

    public final static int MSG_SHOW_MSG = 1001;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u27;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        mPopConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mSendMessenger = new Messenger(iBinder);
                showHidePop();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
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
        RxView.clicks(mShowHidePopView)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    preShowHidePopView();
                });
    }

    private void preShowHidePopView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            if (null == mSendMessenger) {
                Intent intent = new Intent(this, PopService.class);
                bindService(intent, mPopConnection, Context.BIND_AUTO_CREATE);
            } else {
                showHidePop();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.showTextToast("授权失败");
            } else {
                ToastUtil.showTextToast("授权成功");
                preShowHidePopView();
            }
        }
    }

    private void showHidePop() {
        Message msg = new Message();
        msg.what = PopService.MSG_SHOW_HIDE_BTN;
        if (null == mReplyMessenger) {
            HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName());
            handlerThread.start();
            mReplyMessenger = new Messenger(new LocalHandler(handlerThread.getLooper(), this::handleMsg));
        }
        msg.replyTo = mReplyMessenger;
        sendMsg(msg);
    }


    //发送消息
    private void sendMsg(Message msg) {
        if (null == mSendMessenger || null == msg) {
            return;
        }
        try {
            mSendMessenger.send(msg);
        } catch (Exception e) {
        }
    }

    //接收消息
    private void handleMsg(Message msg) {
        if (null == msg) {
            return;
        }
        switch (msg.what) {
            case MSG_SHOW_MSG:
                if (null != msg.getData() && msg.getData().containsKey("msgStr")) {
                    String msgStr = msg.getData().getString("msgStr");
                    ToastUtil.showTextToast(msgStr);
                }
                break;
        }
    }

}