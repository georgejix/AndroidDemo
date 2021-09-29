package com.jx.androiddemo.testactivity.function.f17;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F17Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "ContentProviderActivity";

    @BindView(R.id.textview_content)
    TextView contentTextView;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f17;
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
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    public void add(View view) {
        Uri uri1 = Uri.parse("content://com.jx.androiddemo/students/insert");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", (int) (Math.random() * 1000) + "");
        contentValues.put("name", "11");
        contentValues.put("tel_no", "11");
        contentValues.put("cls_id", "11");
        //获得ContentResolver对象，调用方法
        getContentResolver().insert(uri1, contentValues);
    }

    public void delete(View view) {
    }

    public void alter(View view) {
    }

    public void query(View view) {
    }
}