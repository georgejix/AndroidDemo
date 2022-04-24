package com.jx.androiddemo.testactivity.function.f31to40.f33;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;
import com.jx.androiddemo.testactivity.function.f31to40.f32.AudioPlay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F33Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.rv_audio)
    RecyclerView mAudioRv;

    @Inject
    AudioAdapter mAudioAdapter;

    private String mPath1 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220411173608441.wav";
    private String mPath2 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220331174043925.aac";
    private String mPath3 = "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220222095449261.aac";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f33;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    private void initView() {
        List<String> list = new ArrayList<>();
        list.add(mPath1);
        list.add(mPath2);
        list.add(mPath3);
        mAudioAdapter.init(new Handler());
        mAudioAdapter.addDataAll(list);
        mAudioRv.setAdapter(mAudioAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioPlay.getInstance().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlay.getInstance().quit();
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
}