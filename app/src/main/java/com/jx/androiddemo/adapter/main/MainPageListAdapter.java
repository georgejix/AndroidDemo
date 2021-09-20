package com.jx.androiddemo.adapter.main;

import android.content.Context;

import com.jx.androiddemo.R;
import com.jx.androiddemo.bean.main.MainPageBean;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class MainPageListAdapter extends CommonAdapter<MainPageBean> {
    @Inject
    public MainPageListAdapter(Context context) {
        super(context, R.layout.item_main_page);
    }

    @Override
    protected void convert(ViewHolder holder, MainPageBean bean, int position) {
        holder.setText(R.id.tv_name, bean.name);
    }
}
