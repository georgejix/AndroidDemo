package com.jx.androiddemo.testactivity.ui.u10;

import android.annotation.SuppressLint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U10Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "InputActivity";

    @BindView(R.id.search)
    EditText searchEdit;

    @BindView(R.id.change)
    EditText changeEdit;

    @BindView(R.id.btn_change)
    View btn_change;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u10;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.d(TAG, "IME_ACTION_SEND");
                    handled = true;
                }
                return false;
            }
        });
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
        RxView.clicks(btn_change)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    modifyShowMode();
                });
    }

    private void modifyShowMode() {
        TransformationMethod method = changeEdit.getTransformationMethod();
        if (method instanceof HideReturnsTransformationMethod) {
            changeEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if (method instanceof PasswordTransformationMethod) {
            changeEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else{
            changeEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        changeEdit.setSelection(changeEdit.getText().toString().length());
        ImageView img = new ImageView(this);
    }
}