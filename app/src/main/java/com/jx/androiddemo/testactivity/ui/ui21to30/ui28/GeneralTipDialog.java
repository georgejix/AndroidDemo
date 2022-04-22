package com.jx.androiddemo.testactivity.ui.ui21to30.ui28;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jx.androiddemo.R;


public class GeneralTipDialog extends Dialog {
    private Context mContext;
    private TextView mTitleTv, mLeftTv, mRightTv, mContentTv;
    private View mCloseTv;

    private String mTitle, mContent, mLeftStr, mRightStr;
    private ClickListener mListener;
    private Object[] mObjArray;
    private boolean mCanCancel = true;
    private SpannableStringBuilder mContentBuilder;
    //显示关闭按钮
    private boolean mShowCloseBtn = false;
    private boolean mContentCenter = false;

    public GeneralTipDialog(@NonNull Context context, ClickListener listener) {
        this(context, listener, true);
    }

    public GeneralTipDialog(@NonNull Context context, ClickListener listener, boolean canCancel) {
        super(context);
        mContext = context;
        mListener = listener;
        mCanCancel = canCancel;
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.dialog_general_tip, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTitleTv = contentView.findViewById(R.id.tv_title);
        mContentTv = contentView.findViewById(R.id.tv_content);
        mLeftTv = contentView.findViewById(R.id.tv_left);
        mRightTv = contentView.findViewById(R.id.tv_right);
        mCloseTv = contentView.findViewById(R.id.tv_close);
        super.setContentView(contentView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.CENTER);//设置显示在底部
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(mCanCancel);
        setCanceledOnTouchOutside(mCanCancel);
    }

    @Override
    public void show() {
        super.show();
        mCloseTv.setVisibility(mShowCloseBtn ? View.VISIBLE : View.GONE);
        mContentTv.setGravity(mContentCenter ? Gravity.CENTER : Gravity.START);
        mLeftTv.setOnClickListener((v) -> {
            if (null != mListener) {
                mListener.clickLeft(mContentTv.getText().toString(), mObjArray);
            }
        });
        mRightTv.setOnClickListener((v) -> {
            if (null != mListener) {
                mListener.clickRight(mContentTv.getText().toString(), mObjArray);
            }
        });
        mCloseTv.setOnClickListener((v) -> {
            if (isShowing()) {
                dismiss();
            }
        });
        mTitleTv.setText(mTitle);
        if (null != mContentBuilder) {
            mContentTv.setText(mContentBuilder);
        } else {
            mContentTv.setText(mContent);
        }
        mLeftTv.setText(mLeftStr);
        mRightTv.setText(mRightStr);
    }

    public void setObj(Object... objArray) {
        mObjArray = objArray;
    }

    public void setData(String title, String content, String leftStr, String rightStr) {
        setData(title, content, leftStr, rightStr, false);
    }

    public void setData(String title, String content, String leftStr, String rightStr, boolean showCloseBtn) {
        mTitle = title;
        mContent = content;
        mLeftStr = leftStr;
        mRightStr = rightStr;
        mShowCloseBtn = showCloseBtn;
    }

    public void setData(String title, SpannableStringBuilder content, String leftStr, String rightStr) {
        setData(title, content, leftStr, rightStr, false);
    }

    public void setData(String title, SpannableStringBuilder content, String leftStr, String rightStr, boolean showCloseBtn) {
        setData(title, content, leftStr, rightStr, showCloseBtn, false);
    }

    public void setData(String title, SpannableStringBuilder content, String leftStr, String rightStr, boolean showCloseBtn, boolean contentCenter) {
        mTitle = title;
        mContentBuilder = content;
        mLeftStr = leftStr;
        mRightStr = rightStr;
        mShowCloseBtn = showCloseBtn;
        mContentCenter = contentCenter;
    }

    public interface ClickListener {
        void clickLeft(String content, Object[] obj);

        void clickRight(String content, Object[] obj);
    }

}
