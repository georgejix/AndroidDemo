package com.jx.testtheme

import android.app.Application
import android.content.res.Configuration
import android.util.Log

//手动更换主题颜色
class ThemeApplication : Application() {
    private val TAG = javaClass.simpleName

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
    }
}