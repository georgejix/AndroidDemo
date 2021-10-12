package com.jx.androiddemo.testactivity.function.f28;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.androiddemo.tool.ToastUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F28Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    @BindView(R.id.iv_back)
    View iv_back;
    //标题
    @BindView(R.id.tv_title)
    TextView tv_title;
    //标题箭头
    @BindView(R.id.img_arrow)
    ImageView img_arrow;
    //完成按钮
    @BindView(R.id.tv_complete)
    View tv_complete;
    //视频列表
    @BindView(R.id.rv_video)
    RecyclerView rv_video;
    //下拉
    @BindView(R.id.layout_album)
    View layout_album;
    //相册列表
    @BindView(R.id.rv_album)
    RecyclerView rv_album;
    @BindView(R.id.layout_title)
    View layout_title;

    //视频相册列表
    private List<VideoFolderInfo> mVideoFolderList;

    //子线程handler
    private Handler mBackHandler;
    private ChooseVideoAdapter mChooseVideoAdapter;
    private ChooseAlbumAdapter mChooseAlbumAdapter;
    //选中的视频
    private VideoInfo mVideoInfo;
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f28;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        mChooseVideoAdapter = new ChooseVideoAdapter(mContext, 1);
        mChooseVideoAdapter.setListener((checkedVideList, obj) -> {
            mVideoInfo = checkedVideList.size() > 0 ? checkedVideList.get(0) : null;
            refreshConfirmBtn();
            mChooseVideoAdapter.notifyDataSetChanged();
        });
        rv_video.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_video.setAdapter(mChooseVideoAdapter);

        mChooseAlbumAdapter = new ChooseAlbumAdapter(mContext);
        mChooseAlbumAdapter.setListener((videoFolderInfo, obj) -> refreshAlbum(false));
        rv_album.setLayoutManager(new LinearLayoutManager(mContext));
        rv_album.setAdapter(mChooseAlbumAdapter);

        HandlerThread handlerThread = new HandlerThread("back");
        handlerThread.start();
        mBackHandler = new Handler(handlerThread.getLooper());
        mBackHandler.post(() -> {
            mVideoFolderList = ChooseVideoFromAlbumUtil.getAllVideoFolder(this);
            tv_title.post(() -> refreshAlbum(true));
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
        RxView.clicks(iv_back)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    finish();
                });
        RxView.clicks(layout_title)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    layout_album.setVisibility(View.VISIBLE == layout_album.getVisibility() ? View.GONE : View.VISIBLE);
                    img_arrow.setImageResource(View.VISIBLE == layout_album.getVisibility() ? R.mipmap.icon_arrow_down : R.mipmap.icon_arrow_up);
                    mChooseAlbumAdapter.notifyDataSetChanged();
                });
        RxView.clicks(tv_complete)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    ToastUtil.showTextToast(null != mVideoInfo ? mVideoInfo.getVideoPath() : "");
                });
        RxView.clicks(layout_album)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    layout_album.setVisibility(View.VISIBLE == layout_album.getVisibility() ? View.GONE : View.VISIBLE);
                    img_arrow.setImageResource(View.VISIBLE == layout_album.getVisibility() ? R.mipmap.icon_arrow_down : R.mipmap.icon_arrow_up);
                    mChooseAlbumAdapter.notifyDataSetChanged();
                });
    }
    private void refreshAlbum(boolean init) {
        if (init) {
            mChooseAlbumAdapter.setData(mVideoFolderList);
            mChooseAlbumAdapter.setCheckedIndex(0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv_album.getLayoutParams();
            params.height = (int) (getResources().getDimensionPixelSize(R.dimen.choose_album_height) * (Math.min(mVideoFolderList.size(), 7.5)));
        }
        layout_album.setVisibility(View.GONE);
        img_arrow.setImageResource(R.mipmap.icon_arrow_down);
        VideoFolderInfo videoFolderInfo = mChooseAlbumAdapter.getCheckedFolder();
        if (null == videoFolderInfo) {
            return;
        }
        tv_title.setText(videoFolderInfo.getFolderName());
        mChooseVideoAdapter.setData(videoFolderInfo.getVideoList());
        mChooseVideoAdapter.notifyDataSetChanged();
    }

    //刷新确认按钮样式
    private void refreshConfirmBtn() {
        tv_complete.setBackground(ActivityCompat.getDrawable(mContext, mVideoInfo != null ?
                R.drawable.bg_btn_enable :
                R.drawable.bg_btn_disable));
    }
}