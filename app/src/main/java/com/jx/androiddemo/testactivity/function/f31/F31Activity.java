package com.jx.androiddemo.testactivity.function.f31;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F31Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f31;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        List<AudioFolderInfo> audioFolderInfoList = ChooseAudioFromAlbumUtil.getAllAudioFolder(mContext);
        for (AudioFolderInfo audioFolderInfo : audioFolderInfoList) {
            for (AudioInfo audioInfo : audioFolderInfo.getAudioList()) {
                Log.d(TAG, audioInfo.getAudioPath() + "   " + audioInfo.getAudioId() + "   " + audioInfo.getSize() + "   " + audioInfo.getDuration());
            }
        }
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