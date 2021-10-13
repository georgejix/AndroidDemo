package com.jx.androiddemo.testactivity.function.f30;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.androiddemo.tool.FfmpegTest;

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
        String url = "https://v26-web.douyinvod.com/29195f4d4c577bf0a8e24b08bc0143ef/61665946/video/tos/cn/tos-cn-ve-15/1ce4d80e5d3743f08e2161583a43e104/?a=6383&br=3312&bt=3312&cd=0%7C0%7C0&ch=26&cr=0&cs=0&cv=1&dr=0&ds=4&er=&ft=jal9wj--bz7ThW_TfLct&l=021634093859543fdbddc0200fff0050a9138570000c0f760b4ec&lr=all&mime_type=video_mp4&net=0&pl=0&qs=0&rc=M3V2MzU6ZjVxODMzNGkzM0ApODM4Mzw5OmU5Nzg8OzdkNWc2Xm0wcjRnZmRgLS1kLTBzczQwLy4zNWMwNTM0M2NgL146Yw%3D%3D&vl=&vr=";
        FfmpegTest ffmpegTest = new FfmpegTest();
        ffmpegTest.test();
    }

}