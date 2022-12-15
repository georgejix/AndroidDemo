package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.jx.androiddemo.BaseApplication
import com.jx.androiddemo.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlin.math.abs
import kotlin.math.sqrt

class CutImgLayout : LinearLayout {
    val mContext: Context
    val mRoot: View
    val layout_img: ConstraintLayout
    val img: ImageView
    lateinit var cut_layout: FrameLayout
    lateinit var cut_view: CutView

    enum class TouchEnum {
        DRAG,
        ZOOM
    }

    private var mCutPw = 1
    private var mCutPh = 1
    private var mMaxW = 0
    private var mMaxH = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        mContext = context
        mRoot = LayoutInflater.from(context).inflate(R.layout.layout_cut_layout, null, false)
        layout_img = mRoot.findViewById(R.id.layout_img)
        img = mRoot.findViewById(R.id.img)
        cut_layout = mRoot.findViewById(R.id.cut_layout)
        cut_view = mRoot.findViewById(R.id.cut_view)
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        addView(mRoot)
        cut_view?.setOnTouchListener(mCutTouchListener)
    }

    private var mCutTouchListener = object : View.OnTouchListener {
        var justClick = false//单机事件标识
        var mTouchEnum: TouchEnum? = null//触摸类型

        var downX = 0f//按下x
        var downY = 0f//按下y

        var downDifLength = 0//开始双手缩放时，2点之间距离
        var downWidth = 0//按下图片宽
        var downHeight = 0//按下图片高
        var downMarginTop = 0//按下上边距
        var downMarginLeft = 0//按下上边距

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            event?.apply {
                when (action) {
                    MotionEvent.ACTION_POINTER_2_DOWN -> {
                        //当屏幕上已经有触点(手指)，再有一个手指按下屏幕
                        if (justClick) {
                            justClick = false
                        }
                        if (TouchEnum.ZOOM != mTouchEnum && 2 == pointerCount) {
                            mTouchEnum = TouchEnum.ZOOM
                            downWidth = cut_view.width
                            downHeight = cut_view.height
                            var difX = abs(getX(0) - getX(1))
                            var difY = abs(getY(0) - getY(1))
                            downDifLength = sqrt(
                                Math.pow(difX.toDouble(), 2.0) +
                                        Math.pow(difY.toDouble(), 2.0)
                            ).toInt()
                        }
                        if (cut_view.layoutParams is FrameLayout.LayoutParams) {
                            val params = cut_view.layoutParams as FrameLayout.LayoutParams
                            downMarginTop = params.topMargin
                            downMarginLeft = params.leftMargin
                        }
                    }
                    MotionEvent.ACTION_POINTER_2_UP -> {
                        //当屏幕上已经有手指离开屏幕，屏幕上还有一个手指
                        mTouchEnum = null
                    }
                    MotionEvent.ACTION_DOWN -> {
                        mTouchEnum = TouchEnum.DRAG
                        downX = rawX
                        downY = rawY
                        justClick = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (TouchEnum.DRAG == mTouchEnum) {
                            val changeX = rawX - downX
                            val changeY = rawY - downY
                            if (justClick) {
                                if (abs(changeX) > 10 || abs(changeY) > 10) {
                                    justClick = false
                                }
                            }
                            if (cut_view.layoutParams is FrameLayout.LayoutParams) {
                                val params = cut_view.layoutParams as FrameLayout.LayoutParams
                                val newX = params.leftMargin + changeX.toInt()
                                if (newX >= 0 && newX + cut_view.width <= mMaxW) {
                                    params.leftMargin = params.leftMargin + changeX.toInt()
                                }
                                val newY = params.topMargin + changeY.toInt()
                                if (newY >= 0 && newY + cut_view.height <= mMaxH) {
                                    params.topMargin = params.topMargin + changeY.toInt()
                                }
                                cut_view.layoutParams = params
                            }
                            downX = rawX
                            downY = rawY
                        } else if (TouchEnum.ZOOM == mTouchEnum && 2 == pointerCount) {
                            val difX = abs(getX(0) - getX(1))
                            val difY = abs(getY(0) - getY(1))
                            val currentDifLength = sqrt(
                                Math.pow(difX.toDouble(), 2.0) +
                                        Math.pow(difY.toDouble(), 2.0)
                            ).toInt()
                            val changeLength = currentDifLength - downDifLength
                            var widthChange = changeLength
                            var heightChange = changeLength * mCutPh / mCutPw

                            val maxWidthChange = Math.min(
                                (mMaxW - downWidth - downMarginLeft) * 2, downMarginLeft * 2
                            )
                            val maxHeightChange = Math.min(
                                (mMaxH - downHeight - downMarginTop) * 2, downMarginTop * 2
                            )
                            if (widthChange > maxWidthChange || heightChange > maxHeightChange) {
                                if (maxWidthChange - widthChange < maxHeightChange - heightChange) {
                                    widthChange = maxWidthChange
                                    heightChange = widthChange * mCutPh / mCutPw
                                } else {
                                    heightChange = maxHeightChange
                                    widthChange = heightChange * mCutPw / mCutPh
                                }
                            }

                            if (cut_view.layoutParams is FrameLayout.LayoutParams) {
                                val param = cut_view.layoutParams as FrameLayout.LayoutParams
                                param.width = downWidth + widthChange
                                param.height = downHeight + heightChange
                                param.leftMargin = downMarginLeft - widthChange / 2
                                param.topMargin = downMarginTop - heightChange / 2
                                cut_view.layoutParams = param
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        mTouchEnum = null
                        if (justClick) {
                            onClickLayout()
                        }
                    }
                }
            }
            return true
        }
    }

    private fun onClickLayout() {

    }

    fun setImgSize(
        cutPW: Int, cutPH: Int, imgId: Int, defaultW: Int? = null,
        defaultH: Int? = null, defaultMarginL: Int? = null, defaultMarginT: Int? = null
    ) {
        mCutPw = cutPW
        mCutPh = cutPH
        cut_view.setBitmap(imgId)
        val layoutW = layout_img.width
        val layoutH = layout_img.height
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, imgId, option)
        var imgW = 0
        var imgH = 0

        if (img.layoutParams is ConstraintLayout.LayoutParams) {
            val param1 = img.layoutParams as ConstraintLayout.LayoutParams
            if (layoutW * 1.0 / option.outWidth <= layoutH * 1.0 / option.outHeight) {
                param1.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                param1.height = 0
                param1.dimensionRatio = "h,${option.outWidth}:${option.outHeight}"
                imgW = layoutW
                imgH = layoutW * option.outHeight / option.outWidth
            } else {
                param1.width = 0
                param1.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                param1.dimensionRatio = "w,${option.outWidth}:${option.outHeight}"
                imgH = layoutH
                imgW = layoutH * option.outWidth / option.outHeight
            }
            img.layoutParams = param1
        }

        cut_layout.postInvalidate()
        Glide.with(BaseApplication.getInstance()).load(imgId)
            .apply(bitmapTransform(BlurTransformation(15)))
            .into(img)
        mMaxW = imgW
        mMaxH = imgH

        if (cut_view.layoutParams is FrameLayout.LayoutParams) {
            val param2 = cut_view.layoutParams as FrameLayout.LayoutParams
            if (null != defaultW && null != defaultH) {
                param2.width = defaultW
                param2.height = defaultH
                if (null != defaultMarginL && null != defaultMarginT) {
                    param2.leftMargin = defaultMarginL
                    param2.topMargin = defaultMarginT
                } else {
                    param2.leftMargin = (imgW - param2.width) / 2
                    param2.topMargin = (imgH - param2.height) / 2
                }
            } else {
                val pw = imgW * 1.0 / cutPW
                val ph = imgH * 1.0 / cutPH
                if (pw <= ph) {
                    param2.width = imgW
                    param2.height = imgW * cutPH / cutPW
                } else {
                    param2.height = imgH
                    param2.width = imgH * cutPW / cutPH
                }
                param2.leftMargin = (imgW - param2.width) / 2
                param2.topMargin = (imgH - param2.height) / 2
            }
            cut_view.layoutParams = param2
        }
    }

    fun printParam() {
        Log.d(
            F35Activity.TAG,
            "w=${cut_view.width} h=${cut_view.height} ml=${cut_view.marginLeft} mt=${cut_view.marginTop}"
        )
    }
}