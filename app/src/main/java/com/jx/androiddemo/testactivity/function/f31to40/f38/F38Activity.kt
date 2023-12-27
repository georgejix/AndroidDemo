package com.jx.androiddemo.testactivity.function.f31to40.f38;

import android.annotation.SuppressLint
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.android.synthetic.main.activity_f38.*

class F38Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private var mAdapter: F38Adapter? = null
    private val mF38Util by lazy { F38Util() }

    companion object {
        val TAG0 = "F38Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f38
    }

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
        mAdapter = F38Adapter(mContext)
        val list = ArrayList<F38ListBean>()
        list.add(F38ListBean("runblocking") { mF38Util.testRunBlocking() })
        list.add(F38ListBean("launch") { mF38Util.testLaunch() })
        list.add(F38ListBean("async") { mF38Util.testAsync() })
        list.add(F38ListBean("withContext") { mF38Util.testWithContext() })
        list.add(F38ListBean("suspend") { mF38Util.testSuspend() })
        mAdapter?.setData(list)
        rv.adapter = mAdapter
    }


    @SuppressLint("CheckResult")
    private fun initListener() {
    }

}