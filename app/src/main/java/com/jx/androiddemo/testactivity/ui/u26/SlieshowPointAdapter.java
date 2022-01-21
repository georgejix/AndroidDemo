package com.jx.androiddemo.testactivity.ui.u26;

import android.content.Context;

import androidx.core.app.ActivityCompat;

import com.jx.androiddemo.R;
import com.jx.rvhelper.adapter.CommonAdapter;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

public class SlieshowPointAdapter extends CommonAdapter<String> {
    private int mCheckedIndex = 0;

    @Inject
    public SlieshowPointAdapter(Context context) {
        super(context, R.layout.item_u26_slideshow_point);
    }

    public void setCheckedIndex(int checkedIndex) {
        this.mCheckedIndex = checkedIndex;
    }

    @Override
    protected void convert(ViewHolder holder, String bean, int position) {
        holder.getView(R.id.view).setBackgroundColor(ActivityCompat.getColor(mContext, mCheckedIndex == position ?
                R.color.blue : R.color.grey));
    }
}
