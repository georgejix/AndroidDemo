package com.jx.androiddemo.testactivity.ui.ui11to20.ui13;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U13Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.checkbox_one)
    CheckBox checkBoxOne;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u13;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.selector_u13_checkbox_one);

        //设置drawable对象的大小
        drawable.setBounds(0, 0, 40, 40);

        //设置CheckBox对象的位置，对应为左、上、右、下
        checkBoxOne.setCompoundDrawables(drawable, null, null, null);
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