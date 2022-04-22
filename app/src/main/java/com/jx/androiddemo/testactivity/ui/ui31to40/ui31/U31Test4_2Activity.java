package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.jx.androiddemo.R;

public class U31Test4_2Activity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         *1、打开FEATURE_CONTENT_TRANSITIONS开关(可选)，这个开关默认是打开的
         */
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test4_2);
        initView();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frame, new U31Test4Fragment(this)).commit();
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementReturnTransition(transitionSet);
    }

}
