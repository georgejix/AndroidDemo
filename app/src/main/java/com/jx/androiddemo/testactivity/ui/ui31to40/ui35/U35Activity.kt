package com.jx.androiddemo.testactivity.ui.ui31to40.ui35

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.android.synthetic.main.activity_u35.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.sqrt

class U35Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val mPopMinWidth: Int by lazy { resources.getDimensionPixelOffset(R.dimen.pxtodp300) }
    private val mPopMinHeight: Int by lazy { resources.getDimensionPixelOffset(R.dimen.pxtodp80) }

    enum class TouchEnum {
        DRAG,
        ZOOM
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int = R.layout.activity_u35

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
        layout_pop.setOnTouchListener(mTouchListener)
        img.setOnTouchListener(mImgTouchListener)
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        RxView.clicks(layout_bg)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { v -> printParams() }
    }

    private fun printParams() {
        val params1 = layout_pop.layoutParams as FrameLayout.LayoutParams
        Log.d(
            TAG, "layout_pop: width=${layout_pop.width} height=${layout_pop.height}" +
                    " marginLeft=${params1.leftMargin} marginBottom=${params1.bottomMargin}"
        )
        val params2 = img.layoutParams as FrameLayout.LayoutParams
        Log.d(
            TAG, "layout_pop: width=${img.width} height=${img.height}" +
                    " marginLeft=${params2.leftMargin} marginBottom=${params2.bottomMargin}"
        )
    }

    private fun onClickPopLayout() {

    }

    private var mTouchListener = object : View.OnTouchListener {
        var justClick = false
        var mTouchEnum: TouchEnum? = null

        var downX = 0f
        var downY = 0f

        var downDifX = 0f
        var downDifY = 0f
        var downWidth = 0
        var downHeight = 0
        var downMarginBottom = 0
        var x0Big = false
        var y0Big = false

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
                            downDifX = abs(getX(0) - getX(1))
                            downDifY = abs(getY(0) - getY(1))
                            downWidth = layout_pop.width
                            downHeight = layout_pop.height
                            val params = layout_pop.layoutParams as FrameLayout.LayoutParams
                            downMarginBottom = params.bottomMargin
                            x0Big = getX(0) > getX(1)
                            y0Big = getY(0) > getY(1)
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
                        pointerCount
                        if (TouchEnum.DRAG == mTouchEnum) {
                            val changeX = rawX - downX
                            val changeY = rawY - downY
                            if (justClick) {
                                if (abs(changeX) > 10 || abs(changeY) > 10) {
                                    justClick = false
                                }
                            }
                            val params = layout_pop.layoutParams as FrameLayout.LayoutParams
                            params.leftMargin = layout_pop.marginLeft + changeX.toInt()
                            params.bottomMargin = layout_pop.marginBottom - changeY.toInt()
                            layout_pop.layoutParams = params
                            downX = rawX
                            downY = rawY
                        } else if (TouchEnum.ZOOM == mTouchEnum && 2 == pointerCount) {
                            val changeWidth = (abs(getX(0) - getX(1)) - downDifX).toInt()
                            val changeHeight = (abs(getY(0) - getY(1)) - downDifY).toInt()
                            val params = layout_pop.layoutParams as FrameLayout.LayoutParams
                            if (downWidth + changeWidth >= mPopMinWidth && getX(0) > getX(1) == x0Big) {
                                params.width = downWidth + changeWidth
                            }
                            if (downHeight + changeHeight >= mPopMinHeight && getY(0) > getY(1) == y0Big) {
                                params.height = downHeight + changeHeight
                                params.bottomMargin = downMarginBottom - changeHeight / 2
                            }
                            layout_pop.layoutParams = params
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        mTouchEnum = null
                        if (justClick) {
                            onClickPopLayout()
                        }
                    }
                }
            }
            return true
        }
    }

    private var mImgTouchListener = object : View.OnTouchListener {
        var justClick = false
        var mTouchEnum: TouchEnum? = null

        var downX = 0f
        var downY = 0f

        var downDifLength = 0
        var downWidth = 0

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
                            downWidth = img.width
                            var difX = abs(getX(0) - getX(1))
                            var difY = abs(getY(0) - getY(1))
                            downDifLength = sqrt(
                                Math.pow(difX.toDouble(), 2.0) +
                                        Math.pow(difY.toDouble(), 2.0)
                            ).toInt()
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
                        pointerCount
                        if (TouchEnum.DRAG == mTouchEnum) {
                            val changeX = rawX - downX
                            val changeY = rawY - downY
                            if (justClick) {
                                if (abs(changeX) > 10 || abs(changeY) > 10) {
                                    justClick = false
                                }
                            }
                            val params = img.layoutParams as FrameLayout.LayoutParams
                            params.leftMargin = img.marginLeft + changeX.toInt()
                            params.bottomMargin = img.marginBottom - changeY.toInt()
                            img.layoutParams = params
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
                            val params = img.layoutParams as FrameLayout.LayoutParams
                            if (downWidth + changeLength >= mPopMinWidth) {
                                params.width = downWidth + changeLength
                            }
                            img.layoutParams = params
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        mTouchEnum = null
                        if (justClick) {
                            onClickPopLayout()
                        }
                    }
                }
            }
            return true
        }
    }
}