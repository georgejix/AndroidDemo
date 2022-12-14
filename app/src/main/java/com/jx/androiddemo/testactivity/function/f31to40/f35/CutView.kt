package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.jx.androiddemo.R

class CutView : View {
    private var mContext: Context? = null
    private var mPaint: Paint? = null
    private var mLineWidth = 1

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        mLineWidth = mContext?.resources?.getDimensionPixelSize(R.dimen.pxtodp2) ?: 1

        mPaint = Paint()
        mPaint?.color = Color.parseColor("#80FFFFFF")
        mPaint?.isAntiAlias = true
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = mLineWidth.toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply { mPaint?.let { drawRect(Rect(0, 0, width, height), it) } }
    }
}