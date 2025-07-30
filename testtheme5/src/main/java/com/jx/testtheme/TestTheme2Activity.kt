package com.jx.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import com.jx.testtheme4.R
import com.jx.testtheme4.databinding.ActivityThemeTest2Binding

//手动更换主题颜色
class TestTheme2Activity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    private var mBinding: ActivityThemeTest2Binding? = null
    private val mVm by lazy { ViewModel2() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_theme_test2)
        mBinding?.gvm = GlobalViewModel
        mBinding?.vm2 = mVm
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        updateStr(resources.configuration.uiMode)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        updateStr(newConfig.uiMode)
    }

    private fun updateStr(uiMode: Int) {
        if (uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
            mVm.mStr.set("处于深色模式")
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
            mVm.mStr.set("处于浅色模式")
        }
    }
}
