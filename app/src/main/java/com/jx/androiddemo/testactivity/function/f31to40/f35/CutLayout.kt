package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.jx.androiddemo.R

class CutLayout : FrameLayout {
    private var mContext: Context? = null
    private var mCornerPaint: Paint? = null
    private var mCornerLineWidth = 1
    private var mLineLength = 1

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        mCornerLineWidth = mContext?.resources?.getDimensionPixelSize(R.dimen.pxtodp6) ?: 1
        mLineLength = mContext?.resources?.getDimensionPixelSize(R.dimen.pxtodp28) ?: 1

        mCornerPaint = Paint()
        mCornerPaint?.color = Color.WHITE
        mCornerPaint?.isAntiAlias = true
        mCornerPaint?.style = Paint.Style.STROKE
        mCornerPaint?.strokeWidth = mCornerLineWidth.toFloat()
        setWillNotDraw(false)
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.pic4, option)
        val oW = MeasureSpec.getSize(widthMeasureSpec)
        val oH = MeasureSpec.getSize(heightMeasureSpec)
        val margin = resources.getDimensionPixelOffset(R.dimen.pxtodp40)
        val pW = option.outWidth * 1.0 / (oW - margin)
        val pH = option.outHeight * 1.0 / (oH - margin)
        val p = Math.min(pW, pH)
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(((oW - margin) * p).toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(((oH - margin) * p).toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.apply {
            val clipPath = Path()
            val childView = getChildAt(0)
            if (null != childView) {
                drawBitmap(
                    BitmapFactory.decodeResource(resources, R.mipmap.pic4),
                    0f, 0f, mCornerPaint
                )
                clipPath.addRect(
                    childView.left.toFloat(),
                    childView.top.toFloat(), childView.right.toFloat(),
                    childView.bottom.toFloat(), Path.Direction.CW
                )
                clipPath(clipPath, Region.Op.DIFFERENCE)
                drawColor(Color.parseColor("#8014182F"))

                mCornerPaint?.let {
                    drawRect(
                        Rect(
                            childView.left - mCornerLineWidth, childView.top - mCornerLineWidth,
                            childView.left + mLineLength, childView.top
                        ), it
                    )
                    drawRect(
                        Rect(
                            childView.left - mCornerLineWidth, childView.top - mCornerLineWidth,
                            childView.left, childView.top + mLineLength
                        ),
                        it
                    )

                    drawRect(
                        Rect(
                            childView.right + mCornerLineWidth, childView.top - mCornerLineWidth,
                            childView.right - mLineLength, childView.top
                        ), it
                    )
                    drawRect(
                        Rect(
                            childView.right + mCornerLineWidth, childView.top - mCornerLineWidth,
                            childView.right, childView.top + mLineLength
                        ), it
                    )

                    drawRect(
                        Rect(
                            childView.left - mCornerLineWidth, childView.bottom + mCornerLineWidth,
                            childView.left, childView.bottom - mLineLength
                        ), it
                    )
                    drawRect(
                        Rect(
                            childView.left - mCornerLineWidth, childView.bottom + mCornerLineWidth,
                            childView.left + mLineLength, childView.bottom
                        ), it
                    )

                    drawRect(
                        Rect(
                            childView.right + mCornerLineWidth, childView.bottom + mCornerLineWidth,
                            childView.right - mLineLength, childView.bottom
                        ), it
                    )
                    drawRect(
                        Rect(
                            childView.right + mCornerLineWidth, childView.bottom + mCornerLineWidth,
                            childView.right, childView.bottom - mLineLength
                        ), it
                    )
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}