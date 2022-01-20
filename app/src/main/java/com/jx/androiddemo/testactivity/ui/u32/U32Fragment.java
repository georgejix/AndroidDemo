package com.jx.androiddemo.testactivity.ui.u32;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jx.androiddemo.R;

import org.jetbrains.annotations.NotNull;

public class U32Fragment extends Fragment
{
    private static final String TAG = "U32Fragment";

    private View mRootView;

    private TextView tv_content;

    private boolean mNeedRefresh = true;

    private String mTxt;

    public U32Fragment()
    {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        if (null == savedInstanceState)
        {
            return;
        }
        Log.d(TAG, "after onSaveInstanceState");
    }

    public void setText(String str)
    {
        mTxt = str;
        if (null != tv_content)
        {
            tv_content.setText(mTxt);
        }
    }

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_u32, container, false);
        return mRootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mNeedRefresh)
        {
            initView();
            mNeedRefresh = false;
        }
    }

    private void initView()
    {
        if (null == mRootView)
        {
            return;
        }
        tv_content = mRootView.findViewById(R.id.tv_content);
        tv_content.setText(null == mTxt ? System.currentTimeMillis() + "" : mTxt);
        tv_content.setOnClickListener(v->{
            U32Activity u32Activity= (U32Activity) getActivity();
            u32Activity.refresh();
        });
    }
}
