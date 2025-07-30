package com.jx.testtheme

import android.app.Application
import android.content.res.Configuration
import android.util.Log

//监听主题变化，通过viewBinding自动刷新ui主题
class ThemeApplication : Application() {
    private val TAG = javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        GlobalViewModel.setTheme(resources.configuration.uiMode)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        GlobalViewModel.setTheme(newConfig.uiMode)
    }
}