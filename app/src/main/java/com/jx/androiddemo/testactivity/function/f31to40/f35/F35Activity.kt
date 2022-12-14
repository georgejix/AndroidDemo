package com.jx.androiddemo.testactivity.function.f31to40.f35

import android.view.View
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.activity_f35.*

class F35Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

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
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
        }
    }
}