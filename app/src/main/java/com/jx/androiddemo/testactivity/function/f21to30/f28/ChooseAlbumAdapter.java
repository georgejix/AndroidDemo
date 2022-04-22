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

public class ChooseAlbumAdapter extends RecyclerView.Adapter<ChooseAlbumAdapter.ViewHolder> {

    public final String TAG = getClass().getName();

    private final List<VideoFolderInfo> mContentList = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ClickListener mClickListener;
    private Object[] mObj;
    private long mCheckedId;

    @SuppressLint("SimpleDateFormat")
    public ChooseAlbumAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setListener(ClickListener listener) {
        mClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<VideoFolderInfo> list, Object... obj) {
        mContentList.clear();
        if (null != list) {
            mContentList.addAll(list);
        }
        mObj = obj;
    }

    public void setCheckedIndex(int index) {
        if (index < 0 || index >= mContentList.size()) {
            return;
        }
        mCheckedId = mContentList.get(index).getFolderId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_choose_album, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (mContentList.size() <= position || null == mContentList.get(position)) {
            return;
        }
        VideoFolderInfo bean = mContentList.get(position);
        if (null == bean) {
            return;
        }
        VideoInfo videoInfo = bean.getCoverPhoto();
        //图片
        if (null != bean.getCoverPhoto()) {
            Glide.with(mContext)
                    .setDefaultRequestOptions(
                            new RequestOptions().centerCrop()
                    )
                    .load(bean.getCoverPhoto().getVideoPath())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.img_video);
        } else {
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder.img_video);
        }
        //名称
        holder.tv_name.setText(bean.getFolderName());
        //数量
        holder.tv_count.setText("(" + (bean.getVideoList().size()) + ")");
        //选中
        holder.img_hook.setVisibility(mCheckedId == bean.getFolderId() ? View.VISIBLE : View.GONE);

        //根布局点击
        holder.root.setOnClickListener(v -> {
            mCheckedId = bean.getFolderId();
            if (null != mClickListener) {
                mClickListener.itemClick(bean, mObj);
            }
        });
    }

    public VideoFolderInfo getCheckedFolder() {
        for (VideoFolderInfo videoFolderInfo : mContentList) {
            if (null == videoFolderInfo) {
                continue;
            }
            if (mCheckedId == videoFolderInfo.getFolderId()) {
                return videoFolderInfo;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //根布局
        View root;
        //图片
        ImageView img_video;
        //名称
        TextView tv_name;
        //数量
        TextView tv_count;
        //选中
        ImageView img_hook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            img_video = itemView.findViewById(R.id.img_video);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_count = itemView.findViewById(R.id.tv_count);
            img_hook = itemView.findViewById(R.id.img_hook);
        }
    }

    public interface ClickListener {
        void itemClick(VideoFolderInfo videoFolderInfo, Object[] obj);
    }
}
