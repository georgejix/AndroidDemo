package com.jx.androiddemo.testactivity.ui.ui31to40.ui34

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.jx.androiddemo.R

class DragView : RelativeLayout {
    private val TAG = "DragView"
    private val mContext: Context;
    private var mLastX = 0
    private var mLastY = 0

    private var mMinX = 0f
    private var mMaxX = 0f
    private var mMinY = 0f
    private var mMaxY = 0f

    //x,y差值累加
    private var mDx = 0
    private var mDy = 0

    //小于10，认为是点击事件
    private val MRANGE = 10
    private var view: View
    private var mInit = false

    constructor(context: Context) : super(context) {
        mContext = context
        view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_drag_view, this, false)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_drag_view, this, false)
        initView()
    }

    private fun initView() {
        addView(view)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        initRange()
        event?.apply {
            val x = rawX.toInt() //触摸点相对于屏幕的横坐标
            val y = rawY.toInt() //触摸点相对于屏幕的纵坐标
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    mDy = 0
                    mDx = mDy
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = x - mLastX //两次移动的x距离差
                    val deltaY = y - mLastY //两次移动的y的距离差
                    //重新设置此view相对父容器的偏移量
                    var translationX = translationX + deltaX
                    var translationY = translationY + deltaY
                    //修改坐标范围
                    if (translationX < mMinX) {
                        translationX = mMinX
                    }
                    if (translationX > mMaxX) {
                        translationX = mMaxX
                    }
                    if (translationY < mMinY) {
                        translationY = mMinY
                    }
                    if (translationY > mMaxY) {
                        translationY = mMaxY
                    }
                    setTranslationX(translationX)
                    setTranslationY(translationY)
                    //累加偏移量
                    mDx += Math.abs(deltaX)
                    mDy += Math.abs(deltaY)
                }
                MotionEvent.ACTION_UP -> {
                    //偏移量小于阈值，将事件传递给点击事件
                    if (mDx < MRANGE && mDy < MRANGE) {
                        performClick()
                    }
                    if (translationX != mMinX && translationX != mMaxX) {
                        setTranslationX(if (Math.abs(mMinX - translationX) < Math.abs(mMaxX - translationX)) mMinX else mMaxX)
                    }
                }
                else -> {
                }
            }
            //记录上一次移动的坐标
            mLastX = x
            mLastY = y
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun initRange() {
        if (mInit || null == parent || parent !is View) {
            return
        }
        //设置最大、最小偏移量
        mMinX = (0 - (parent as View).width + width).toFloat()
        mMaxX = 0f
        mMinY = (0 - (parent as View).height + height).toFloat()
        mMaxY = 0f
        mInit = true
        Log.d(TAG, "mMinX=${mMinX},mMaxX=${mMaxX},mMinY=${mMinY},mMaxY=${mMaxY},width=${width}")
    }
}