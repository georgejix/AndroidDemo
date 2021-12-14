package com.jx.androiddemo.testactivity.ui.u31;

import android.app.Activity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.jx.androiddemo.R;

public class TransitionActivity extends Activity {
    private TextView tv_hello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_transition);
        tv_hello = findViewById(R.id.tv_hello);

        /**
         * 1、设置相同的TransitionName
         */
        ViewCompat.setTransitionName(tv_hello, "tv_hello");
        /**
         * 2、设置WindowTransition,除指定的ShareElement外，其它所有View都会执行这个Transition动画
         */
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        /**
         * 3、设置ShareElementTransition,指定的ShareElement会执行这个Transiton动画
         */
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTarget(tv_hello);
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
    }
}
