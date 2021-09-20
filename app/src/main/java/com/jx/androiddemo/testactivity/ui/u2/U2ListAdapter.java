package com.jx.androiddemo.testactivity.ui.u2;

import android.content.Context;

import com.jx.androiddemo.R;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class U2ListAdapter extends CommonAdapter<String> {
    @Inject
    public U2ListAdapter(Context context) {
        super(context, R.layout.item_u2_list);
    }

    @Override
    protected void convert(ViewHolder holder, String bean, int position) {
        holder.setText(R.id.tv_name, bean);
    }
}
