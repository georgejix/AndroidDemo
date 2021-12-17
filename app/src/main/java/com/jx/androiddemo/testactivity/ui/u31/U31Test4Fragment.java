package com.jx.androiddemo.testactivity.ui.u31;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.jx.androiddemo.R;

import org.jetbrains.annotations.NotNull;

public class U31Test4Fragment extends Fragment {
    private Context mContext;
    private int mIndex;
    private TextView tv_hello1;

    public U31Test4Fragment(Context context) {
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_u31_test4, container, false);
        tv_hello1 = view.findViewById(R.id.tv_hello1);
        ViewCompat.setTransitionName(tv_hello1, "tv_hello");
        return view;
    }
}
