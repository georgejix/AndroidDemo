package com.jx.androiddemo.adapter.main;

import android.content.Context;

import com.jx.androiddemo.R;
import com.jx.androiddemo.bean.main.DemoMulTypeBean;
import com.jx.rvhelper.adapter.MultiItemTypeAdapter;
import com.jx.rvhelper.base.ItemViewDelegate;
import com.jx.rvhelper.base.ViewHolder;

import javax.inject.Inject;

//样例，多类型adapter
public class DemoMulTypeAdapter extends MultiItemTypeAdapter<DemoMulTypeBean> {
    private static String TAG = "DemoMulTypeAdapter";

    private Context mContext;

    @Inject
    public DemoMulTypeAdapter(Context context) {
        super(context);
        mContext = context;
        addItemViewDelegate(new HeadDelegate());
        addItemViewDelegate(new ContentDelegate());
    }

    private class HeadDelegate implements ItemViewDelegate<DemoMulTypeBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_main_page;
        }

        @Override
        public boolean isForViewType(DemoMulTypeBean item, int position) {
            return 2 == item.type;
        }

        @Override
        public void convert(ViewHolder holder, DemoMulTypeBean item, int position) {
        }
    }

    private class ContentDelegate implements ItemViewDelegate<DemoMulTypeBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_main_page;
        }

        @Override
        public boolean isForViewType(DemoMulTypeBean item, int position) {
            return 1 == item.type;
        }

        @Override
        public void convert(ViewHolder holder, DemoMulTypeBean item, int position) {
        }
    }
}