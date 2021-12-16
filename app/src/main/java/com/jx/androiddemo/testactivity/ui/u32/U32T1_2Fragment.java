package com.jx.androiddemo.testactivity.ui.u32;

import android.content.Context;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
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

import java.util.List;
import java.util.Map;

public class U32T1_2Fragment extends Fragment
{
    private Context mContext;

    private Listener mListener;

    public TextView tv_hello1;

    public U32T1_2Fragment(Context context, Listener listener)
    {
        mContext = context;
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_u32_t1_2, container, false);
        tv_hello1 = view.findViewById(R.id.tv_hello1);
        tv_hello1.setOnClickListener(v -> {
            if (null != mListener)
            {
                mListener.click();
            }
        });
        ViewCompat.setTransitionName(tv_hello1, "tv_hello1");
        setExitTransition(new Slide(Gravity.LEFT));
        setEnterTransition(new Slide(Gravity.RIGHT));
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

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

    public interface Listener
    {
        void click();
    }
}
