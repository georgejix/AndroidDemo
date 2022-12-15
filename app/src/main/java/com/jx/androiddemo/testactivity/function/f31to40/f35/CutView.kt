package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.jx.androiddemo.R

class CutView : View {
    private val mContext: Context
    private var mCornerPaint: Paint? = null
    private var mPaint: Paint? = null
    private var mCornerLineWidth = 1
    private var mLineWidth = 1
    private var mLineLength = 1

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        mContext = context
        init(context)
    }

    private fun init(context: Context) {
        mCornerLineWidth = mContext.resources?.getDimensionPixelSize(R.dimen.pxtodp6) ?: 1
        mLineWidth = mContext.resources?.getDimensionPixelSize(R.dimen.pxtodp2) ?: 1
        mLineLength = mContext.resources?.getDimensionPixelSize(R.dimen.pxtodp28) ?: 1

        mCornerPaint = Paint()
        mCornerPaint?.color = Color.WHITE
        mCornerPaint?.isAntiAlias = true
        mCornerPaint?.style = Paint.Style.STROKE
        mCornerPaint?.strokeWidth = mCornerLineWidth.toFloat()

        mPaint = Paint()
        mPaint?.color = Color.parseColor("#80FFFFFF")
        mPaint?.isAntiAlias = true
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = mLineWidth.toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(F35Activity.TAG, "marginLeft=${marginLeft} marginTop=${marginTop}")
        Log.d(F35Activity.TAG, "width=${width} height=${height}")
        canvas?.apply {
            mCornerPaint?.let {
                drawRect(
                    Rect(
                        -mCornerLineWidth, -mCornerLineWidth,
                        mLineLength, 0
                    ), it
                )
                drawRect(
                    Rect(-mCornerLineWidth, -mCornerLineWidth, 0, mLineLength),
                    it
                )
                drawRect(
                    Rect(
                        width + mCornerLineWidth, -mCornerLineWidth,
                        width - mLineLength, 0
                    ), it
                )
                drawRect(
                    Rect(
                        width + mCornerLineWidth, -mCornerLineWidth,
                        width, mLineLength
                    ), it
                )
                drawRect(
                    Rect(
                        -mCornerLineWidth, height + mCornerLineWidth,
                        0, height - mLineLength
                    ), it
                )
                drawRect(
                    Rect(
                        -mCornerLineWidth, height + mCornerLineWidth,
                        mLineLength, height
                    ), it
                )
                drawRect(
                    Rect(
                        width + mCornerLineWidth, height + mCornerLineWidth,
                        width - mLineLength, height
                    ), it
                )
                drawRect(
                    Rect(
                        width + mCornerLineWidth, height + mCornerLineWidth,
                        width, height - mLineLength
                    ), it
                )
            }
            mPaint?.let {
                val w = mLineWidth
                drawRect(
                    Rect(
                        0 + w, 0 + w,
                        width - w, height - w
                    ), it
                )
            }
        }
    }
}