package com.jx.androiddemo.testactivity.ui.u25;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jx.androiddemo.R;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class ViewPagerAdapter extends CommonAdapter<String> {
    @Inject
    public ViewPagerAdapter(Context context) {
        super(context, R.layout.item_u25_slideshow_pic);
    }

    @Override
    protected void convert(ViewHolder holder, String bean, int position) {
        ImageView img = holder.getView(R.id.img);
        Glide.with(mContext).load(bean).into(img);
    }
}
