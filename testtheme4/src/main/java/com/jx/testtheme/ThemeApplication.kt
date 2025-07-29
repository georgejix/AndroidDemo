package com.jx.testtheme

import android.app.Application
import android.content.res.Configuration
import android.util.Log

//不监听主题变化，由sdk调用onCreate()
class ThemeApplication : Application() {
    private val TAG = javaClass.simpleName

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
    }
}