package com.jx.androiddemo.testactivity.ui.ui31to40.ui36

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import java.util.concurrent.TimeUnit

class U36Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val layout_bg by lazy { findViewById<View>(R.id.layout_bg) }
    private val img by lazy { findViewById<ImageView>(R.id.img) }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int = R.layout.activity_u36

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
        Glide.with(mContext).load(R.mipmap.pic4).into(img)
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        RxView.clicks(layout_bg)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { v -> mirror2() }
    }

    private fun mirror2() {
        img.scaleX = -img.scaleX
    }

    private fun mirror() {
        img.setImageBitmap(toHorizontalMirror(img.drawable.toBitmap()))
    }

    private fun toHorizontalMirror(bmp: Bitmap): Bitmap {
        val w = bmp.width
        val h = bmp.height
        val matrix = Matrix()
        matrix.postScale(-1f, 1f) // 水平镜像翻转
        return Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true)
    }

    private fun toVerticalMirror(bmp: Bitmap): Bitmap {
        val w = bmp.width
        val h = bmp.height
        val matrix = Matrix()
        matrix.postScale(1f, -1f) // 垂直镜像翻转
        return Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true)
    }
}