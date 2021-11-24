package com.jx.androiddemo.testactivity.ui.u28;

import android.annotation.SuppressLint;
import android.view.View;

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

public class U28Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private GeneralTipDialog mTipDialog;

    @BindView(R.id.tv_show)
    View mShow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u28;
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
        RxView.clicks(mShow)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    showDialog();
                });
    }

    private void showDialog() {
        if (null == mTipDialog) {
            mTipDialog = new GeneralTipDialog(mContext, new GeneralTipDialog.ClickListener() {
                @Override
                public void clickLeft(String content, Object[] obj) {
                    if (null != mTipDialog && mTipDialog.isShowing()) {
                        mTipDialog.dismiss();
                    }
                }

                @Override
                public void clickRight(String content, Object[] obj) {
                    if (null != mTipDialog && mTipDialog.isShowing()) {
                        mTipDialog.dismiss();
                    }
                    if (null != obj && obj.length > 0 && obj[0] instanceof Long) {
                        ToastUtil.showTextToast(Long.toString((Long) obj[0]));
                    }
                }
            });
        }
        mTipDialog.setData("提示",
                "确定么",
                "取消",
                "确定");
        mTipDialog.setObj(System.currentTimeMillis());
        if (!mTipDialog.isShowing()) {
            mTipDialog.show();
        }
    }
}