package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.graphics.BitmapFactory
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.jx.androiddemo.BaseApplication
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.activity_f35.*

class F35Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    companion object {
        val TAG = "F35Activity"
    }

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f35
    }

    override fun initEventAndData() {
        initView()
    }

    private fun initView() {
        if (layout_img.width <= 0) {
            layout_img.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View?, left: Int, top: Int, right: Int,
                    bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
                ) {
                    if (layout_img.width > 0) {
                        layout_img.removeOnLayoutChangeListener(this)
                    }
                    setImgSize()
                }
            })
        } else {
            setImgSize()
        }
    }

    private fun setImgSize(cutPW: Int = 1, cutPH: Int = 1, imgId: Int = R.mipmap.pic5) {
        val layoutW = layout_img.width
        val layoutH = layout_img.height
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, imgId, option)
        val param1 = img.layoutParams as ConstraintLayout.LayoutParams
        var imgW = 0
        var imgH = 0
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
        cut_layout.postInvalidate()
        Glide.with(BaseApplication.getInstance()).load(imgId).into(img)

        val param2 = cut_view.layoutParams as FrameLayout.LayoutParams
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
        cut_view.layoutParams = param2
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
        }
    }
}