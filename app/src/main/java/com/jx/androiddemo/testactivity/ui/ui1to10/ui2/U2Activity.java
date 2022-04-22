package com.jx.androiddemo.testactivity.ui.ui1to10.ui2;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.pullrefreshview.SmartRefreshLayout;
import com.jx.pullrefreshview.api.RefreshLayout;
import com.jx.pullrefreshview.listener.OnRefreshLoadMoreListener;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U2Activity extends BaseMvpActivity<U2Presenter> implements U2Contract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    U2ListAdapter mU2ListAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u2;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    mPresenter.refreshData();
                });
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mU2ListAdapter);
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.refreshData();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    public void onFinishLoading() {
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void onFinishLoadingMore() {
        mSmartRefreshLayout.finishLoadMore();

    }

    @Override
    public void noMoreData() {
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void refreshRecyclerView() {
        mU2ListAdapter.clearData();
        mU2ListAdapter.addDataAll(mPresenter.getData());
        mU2ListAdapter.notifyDataSetChanged();
    }
}