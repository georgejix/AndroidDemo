package com.jx.androiddemo.testactivity.function.f30;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
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
        String url2 = "https://vd2.bdstatic.com/mda-mjbzwb7wzwnuvqhj/sc/cae_h264/1634083355852877186/mda-mjbzwb7wzwnuvqhj.mp4";
        String url = "https://v26-web.douyinvod.com/ba6cbd9169a101377e1b052a933ff956/6167fb2b/video/tos/cn/tos-cn-ve-15/00c85cf7f35c4254810abdf60b9a9460/?a=6383&br=2815&bt=2815&cd=0%7C0%7C0&ch=26&cr=0&cs=0&cv=1&dr=0&ds=4&er=&ft=jal9wj--bz7ThWrr.Lct&l=021634200847341fdbddc0200fff0050a18718f0000001cee4b49&lr=all&mime_type=video_mp4&net=0&pl=0&qs=0&rc=M3lscDY6ZjxzODMzNGkzM0ApaGc2OzY4OmQ6NzUzNDk0PGdnZWdecjRnNWdgLS1kLWFzc2MuLi8uMzZfMDReLjZjLi86Yw%3D%3D&vl=&vr=";
        String out = BaseApplication.getFile() + File.separator + "temp2.mp3";

        FfmpegTest ffmpegTest = new FfmpegTest();
        ffmpegTest.test(url2, out);
    }

}