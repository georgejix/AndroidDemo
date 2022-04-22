package com.jx.androiddemo.testactivity.ui.ui1to10.ui8;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by jix on 2019/3/19.
 */

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView {
    private final String TAG = "MyTextView";

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        return true;
    }
}
