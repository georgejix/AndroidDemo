package com.jx.androiddemo.testactivity.ui.ui11to20.ui19;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U19Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.layout_bezierview)
     LinearLayout bezierViewLayout;
    @BindView(R.id.bezierview)
     BezierView bezierView2;

    private List<BezierView.Point> pointList;
    private BezierView bezierView;
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u19;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        bezierView = new BezierView(this);
        pointList = new ArrayList<>();
        pointList.add(bezierView.new Point(5,5));
        pointList.add(bezierView.new Point(50,50));
        pointList.add(bezierView.new Point(100,99));
        pointList.add(bezierView.new Point(150,35));
        pointList.add(bezierView.new Point(200,77));
        pointList.add(bezierView.new Point(250,44));
        bezierView.setPointList(pointList);
        bezierViewLayout.addView(bezierView);

        bezierView2.setPointList(pointList);
        bezierView2.invalidate();
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