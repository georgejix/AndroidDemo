package com.jx.androiddemo.testactivity.ui.ui1to10.ui4;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.widget.ListView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U4Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.listview)
    ListView listView;

    private Adapter4TestBitmap adapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u4;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        if (null == adapter) {
            if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                adapter = new Adapter4TestBitmap(this, new File(Environment.getExternalStorageDirectory() + File.separator + "pic"));
            }

            listView.setAdapter(adapter);
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