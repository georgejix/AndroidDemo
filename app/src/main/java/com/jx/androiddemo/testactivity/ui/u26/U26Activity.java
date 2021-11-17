package com.jx.androiddemo.testactivity.ui.u26;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U26Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.auto_scroll_vp)
    AutoScrollViewPager mAutoScrollViewPager;

    @BindView(R.id.rv_point)
    RecyclerView mPointRv;

    private PointAdapter mPointAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u26;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        List<String> tempAdvList = new ArrayList();
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffile01.16sucai.com%2Fd%2Ffile%2F2011%2F1007%2F20111007124744976.jpg&refer=http%3A%2F%2Ffile01.16sucai.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=6c70e33c44c5b0c2b8e52299787df171");
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffile02.16sucai.com%2Fd%2Ffile%2F2014%2F0427%2F071875652097059bbbffe106f9ce3a93.jpg&refer=http%3A%2F%2Ffile02.16sucai.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=a3864e40c07c07bc5e4aaf30a1a5b278");
        tempAdvList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp05%2F1Z9291J4442Y1-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639624199&t=f79003c73c175773791316899df4da3d");

        mAutoScrollViewPager.setData(tempAdvList);
        mAutoScrollViewPager.setListener((position, totalCount) -> {
            Log.d(TAG, position + "," + totalCount);
            if (null == mPointAdapter) {
                return;
            }
            mPointAdapter.setCheckedIndex(position);
            mPointAdapter.notifyDataSetChanged();
        });

        mPointRv.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        mPointAdapter = new PointAdapter(mContext);
        mPointAdapter.addDataAll(tempAdvList);
        mPointRv.setAdapter(mPointAdapter);
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

    @Override
    protected void onPause() {
        super.onPause();
        mAutoScrollViewPager.stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoScrollViewPager.startTimer();
    }
}