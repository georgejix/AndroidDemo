package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

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

public class U31Test6_2Activity extends Activity {
    private TextView tv_hello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test6_2);
        tv_hello = findViewById(R.id.tv_hello);

        /**
         * 1、设置相同的TransitionName
         */
        ViewCompat.setTransitionName(tv_hello, "tv_hello");
    }
}
