package com.jx.testtheme

import android.app.Application
import android.content.res.Configuration
import android.util.Log

//onCreate设置主题，更换时设置主题并调用recreate()
class ThemeApplication : Application() {
    private val TAG = javaClass.simpleName

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
    }
}