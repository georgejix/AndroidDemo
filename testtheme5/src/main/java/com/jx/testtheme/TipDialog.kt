package com.jx.testtheme

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jx.testtheme5.databinding.LayoutDialogBinding

class TipDialog(val mContext: Context) : Dialog(mContext) {
    init {
        val view = LayoutDialogBinding.inflate(LayoutInflater.from(mContext), null, false)
        view.gvm = GlobalViewModel
        setContentView(view.root)
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