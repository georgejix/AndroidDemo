package com.jx.androiddemo.testactivity.ui.u30;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jx.androiddemo.R;


public class GeneralLoadingView extends View {
    private Paint mPaint;
    private int mStartDegree = 0;

    public GeneralLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public GeneralLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int color = 0xff4349a9;
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GeneralLoading);
            color = a.getColor(R.styleable.GeneralLoading_loading_color, 0xff4349a9);
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        RectF rect = new RectF(centerX * 0.5f, -centerX * 0.08f, centerX * 0.99f, centerX * 0.08f);
        for (int i = 0; i < 12; i++) {
            canvas.save();

            canvas.translate(centerX, centerY);
            canvas.rotate(30 * i + mStartDegree);
            mPaint.setAlpha(255 / 12 * (12 - i));
            canvas.drawRect(rect, mPaint);

            canvas.restore();
        }
        postDelayed(() -> {
            mStartDegree = (mStartDegree + 30) % 360;
            invalidate();
        }, 50);
    }

}
