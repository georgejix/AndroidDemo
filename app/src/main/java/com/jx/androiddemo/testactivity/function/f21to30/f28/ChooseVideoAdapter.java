package com.jx.androiddemo.testactivity.function.f21to30.f28;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jx.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseVideoAdapter extends RecyclerView.Adapter<ChooseVideoAdapter.ViewHolder> {

    public final String TAG = getClass().getName();

    private final List<VideoInfo> mContentList = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ClickListener mClickListener;
    private Object[] mObj;
    private List<Long> mCheckIdList = new ArrayList<>();
    private List<VideoInfo> mCheckVideoList = new ArrayList<>();
    private int mMaxSize;

    @SuppressLint("SimpleDateFormat")
    public ChooseVideoAdapter(Context context, int maxSize) {
        this.mContext = context;
        mMaxSize = maxSize;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setListener(ClickListener listener) {
        mClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<VideoInfo> list, Object... obj) {
        mContentList.clear();
        if (null != list) {
            mContentList.addAll(list);
        }
        mObj = obj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_choose_video, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (mContentList.size() <= position || null == mContentList.get(position)) {
            return;
        }
        VideoInfo bean = mContentList.get(position);
        //时间
        holder.tv_time.setText((bean.getDuration() / 1000) + "s");
        //图片
        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions().centerCrop()
                )
                .load(bean.getVideoPath())
                .error(R.mipmap.ic_launcher)
                .into(holder.img_video);
        //选中
        holder.img_checked.setSelected(mCheckIdList.contains(bean.getVideoId()));

        //根布局点击
        holder.root.setOnClickListener(v -> {
            clickVideo(bean);
        });
    }

    private void clickVideo(VideoInfo bean) {
        if (1 == mMaxSize) {
            if (mCheckIdList.contains(bean.getVideoId())) {
                mCheckIdList.remove(bean.getVideoId());
                mCheckVideoList.remove(bean);
            } else {
                mCheckIdList.clear();
                mCheckIdList.add(bean.getVideoId());
                mCheckVideoList.clear();
                mCheckVideoList.add(bean);
            }
        } else {
            if (mCheckIdList.contains(bean.getVideoId())) {
                mCheckIdList.remove(bean.getVideoId());
                mCheckVideoList.remove(bean);
            } else {
                if (mCheckIdList.size() >= mMaxSize) {
                    return;
                }
                mCheckIdList.add(bean.getVideoId());
                mCheckVideoList.add(bean);
            }
        }
        if (null != mClickListener) {
            mClickListener.itemClick(mCheckVideoList, mObj);
        }
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //根布局
        View root;
        //时间
        TextView tv_time;
        //图片
        ImageView img_video;
        //选择
        ImageView img_checked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            tv_time = itemView.findViewById(R.id.tv_time);
            img_video = itemView.findViewById(R.id.img_video);
            img_checked = itemView.findViewById(R.id.img_checked);
        }
    }

    public interface ClickListener {
        void itemClick(List<VideoInfo> checkedVideList, Object[] obj);
    }
}
