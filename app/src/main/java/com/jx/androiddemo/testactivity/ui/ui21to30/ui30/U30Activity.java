package com.jx.androiddemo.testactivity.ui.ui21to30.ui30;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loading.AVLoadingIndicatorView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U30Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.rv)
    RecyclerView mRv;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u30;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(new RecyclerView.Adapter<IndicatorHolder>() {
            @Override
            public IndicatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = getLayoutInflater().inflate(R.layout.item_indicator, parent, false);
                return new IndicatorHolder(itemView);
            }

            @Override
            public void onBindViewHolder(IndicatorHolder holder, final int position) {
                holder.indicatorView.setIndicator(INDICATORS[position]);
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(U30Activity.this, IndicatorActivity.class);
                        intent.putExtra("indicator", INDICATORS[position]);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return INDICATORS.length;
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
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    final static class IndicatorHolder extends RecyclerView.ViewHolder {

        public AVLoadingIndicatorView indicatorView;
        public View itemLayout;

        public IndicatorHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            indicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.indicator);
        }
    }


    private static final String[] INDICATORS = new String[]{
            "BallPulseIndicator",
            "BallGridPulseIndicator",
            "BallClipRotateIndicator",
            "BallClipRotatePulseIndicator",
            "SquareSpinIndicator",
            "BallClipRotateMultipleIndicator",
            "BallPulseRiseIndicator",
            "BallRotateIndicator",
            "CubeTransitionIndicator",
            "BallZigZagIndicator",
            "BallZigZagDeflectIndicator",
            "BallTrianglePathIndicator",
            "BallScaleIndicator",
            "LineScaleIndicator",
            "LineScalePartyIndicator",
            "BallScaleMultipleIndicator",
            "BallPulseSyncIndicator",
            "BallBeatIndicator",
            "LineScalePulseOutIndicator",
            "LineScalePulseOutRapidIndicator",
            "BallScaleRippleIndicator",
            "BallScaleRippleMultipleIndicator",
            "BallSpinFadeLoaderIndicator",
            "LineSpinFadeLoaderIndicator",
            "TriangleSkewSpinIndicator",
            "PacmanIndicator",
            "BallGridBeatIndicator",
            "SemiCircleSpinIndicator",
            "com.wang.avi.sample.MyCustomIndicator"
    };
}