package com.jx.androiddemo.testactivity.ui.u25;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jx.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterOld extends RecyclerView.Adapter<ViewPagerAdapterOld.ViewHolder> {
    public final String TAG = getClass().getName();

    private final List<String> mContentList = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private Object[] mObj;

    @SuppressLint("SimpleDateFormat")
    public ViewPagerAdapterOld(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<String> list, Object... obj) {
        mContentList.clear();
        if (null != list) {
            mContentList.addAll(list);
        }
        mObj = obj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_u25_view_pager, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mContext).load(mContentList.get(position % mContentList.size())).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return null == mContentList ? 0 : Integer.MAX_VALUE;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //根布局
        View root;
        //图片
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            img = itemView.findViewById(R.id.img);
        }
    }
}