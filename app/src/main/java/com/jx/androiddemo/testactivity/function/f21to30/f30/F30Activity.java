package com.jx.androiddemo.testactivity.function.f21to30.f30;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;
import com.jx.androiddemo.tool.FfmpegTest;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F30Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_download)
    TextView tv_download;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f30;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
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
        RxView.clicks(tv_download)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    download();
                });
    }

    private void download() {
        String url2 = BaseApplication.getInstance().getFile() + File.separator + "temp6.mp4";
        String out = getExternalCacheDir() + File.separator + "f30.wav";
        Log.d(TAG, "in=" + url2 + ",out=" + out);

        File f = new File(out);
        if (f.exists()) {
            f.delete();
        }

        FfmpegTest ffmpegTest = new FfmpegTest();
        ffmpegTest.test2(url2, out);
    }

}