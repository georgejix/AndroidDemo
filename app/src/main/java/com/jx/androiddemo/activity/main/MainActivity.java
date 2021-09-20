package com.jx.androiddemo.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.adapter.main.MainPageListAdapter;
import com.jx.androiddemo.contract.main.MainContract;
import com.jx.androiddemo.presenter.main.MainPresenter;
import com.jx.androiddemo.tool.PermissionUtil;
import com.jx.rvhelper.adapter.MultiItemTypeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.rv_page)
    RecyclerView mPageRV;

    @Inject
    MainPageListAdapter mMainPageListAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        checkPermission();
    }

    private void initView() {
        mPageRV.setLayoutManager(new LinearLayoutManager(this));
        mMainPageListAdapter.clearData();
        mMainPageListAdapter.addDataAll(mPresenter.getMainPageList());
        mMainPageListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                if (null != mPresenter.getMainPageList().get(position).clazz) {
                    startActivity(new Intent(mContext, mPresenter.getMainPageList().get(position).clazz));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        mPageRV.setAdapter(mMainPageListAdapter);
    }

    private void checkPermission() {
        List<String> permissionLists = PermissionUtil.getNeedRequirePermission();
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (0 == requestCode) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                showMsg(getString(R.string.access_camera_permission));
            }
        }
    }
}