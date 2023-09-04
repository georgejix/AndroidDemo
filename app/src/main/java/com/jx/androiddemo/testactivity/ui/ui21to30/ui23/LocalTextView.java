package com.jx.androiddemo.testactivity.ui.ui21to30.ui23;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

public class LocalTextView extends AppCompatTextView {
    private final String TAG = "layout_measure";
    public LocalTextView(Context context) {
        super(context);
    }

    public LocalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"LocalTextView onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"LocalTextView onMeasure");
    }

}
