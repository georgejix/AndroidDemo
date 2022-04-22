package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

import android.content.Context;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
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
        setExitTransition(new Slide(Gravity.LEFT));
        setEnterTransition(new Slide(Gravity.RIGHT));
        /*setSharedElementEnterTransition(TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition));*/
        //设置进入共享动画
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        setSharedElementEnterTransition(transitionSet);
        setSharedElementReturnTransition(transitionSet);
        /*setEnterSharedElementCallback(new SharedElementCallback()
        {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements)
            {
                sharedElements.put(names.get(0),tv_hello1);
            }
        });*/
        return view;
    }

    public interface Listener {
        void click();
    }
}
