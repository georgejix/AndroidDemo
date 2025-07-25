package com.jx.androiddemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.jx.androiddemo.R
import com.jx.androiddemo.tool.DisplayUtils

class GeneralTitleLayout : RelativeLayout {
    private lateinit var mContext: Context

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    fun initView(context: Context) {
        mContext = context
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.layout_general_title, null, false)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = params
        addView(view)
        val toolBarHeight: Int = DisplayUtils.getToolBarHeight(mContext)
        if (toolBarHeight > 0) {
            view.findViewById<View>(R.id.layout_title_bar).layoutParams.height = toolBarHeight
        }
    }
}