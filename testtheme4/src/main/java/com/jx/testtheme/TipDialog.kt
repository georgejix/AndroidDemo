package com.jx.testtheme

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jx.testtheme4.R

class TipDialog(val mContext: Context) : Dialog(mContext) {
    init {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog, null)
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setGravity(Gravity.CENTER) //设置显示在底部
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }
}