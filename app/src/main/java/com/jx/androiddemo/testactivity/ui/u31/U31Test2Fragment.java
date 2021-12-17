package com.jx.androiddemo.testactivity.ui.u31;

import android.content.Context;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
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

public class U31Test2Fragment extends Fragment {
    private Context mContext;
    private Listener mListener;
    public TextView tv_hello1;

    public U31Test2Fragment(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_u31_test2, container, false);
        tv_hello1 = view.findViewById(R.id.tv_hello1);
        tv_hello1.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.click();
            }
        });
        ViewCompat.setTransitionName(tv_hello1, "tv_hello");
        setExitTransition(new Slide(Gravity.RIGHT));
        setEnterTransition(new Slide(Gravity.LEFT));
        /*setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));*/
       /* TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new Fade());
        transitionSet.addTarget(tv_hello1);
        setExitTransition(transitionSet);*/
        /*setExitSharedElementCallback(new SharedElementCallback()
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
