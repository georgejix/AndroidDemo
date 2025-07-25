package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.view.View
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil

class F35Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val layout_cut_img by lazy { findViewById<CutImgLayout>(R.id.layout_cut_img) }
    private val tv_print by lazy { findViewById<View>(R.id.tv_print) }

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
        layout_cut_img.postDelayed({
            layout_cut_img.visibility = View.VISIBLE
            layout_cut_img.setImgSize(1, 1, R.mipmap.pic5, 300, 300, 100, 50)
        }, 500)
        tv_print.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
            R.id.tv_print -> layout_cut_img.printParam()
        }
    }
}