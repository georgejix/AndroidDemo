package com.jx.androiddemo.testactivity.ui.u31;

import android.content.Context;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.jx.androiddemo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class U31Test2_2Fragment extends Fragment {
    private Context mContext;
    private Listener mListener;
    public TextView tv_hello1;

    public U31Test2_2Fragment(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_u31_test2_2, container, false);
        tv_hello1 = view.findViewById(R.id.tv_hello1);
        tv_hello1.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.click();
            }
        });
        ViewCompat.setTransitionName(tv_hello1, "tv_hello");
        setEnterTransition(new Fade());
        setExitTransition(new Fade());
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        setSharedElementEnterTransition(transitionSet);
        setSharedElementReturnTransition(transitionSet);
        return view;
    }

    public interface Listener {
        void click();
    }
}
