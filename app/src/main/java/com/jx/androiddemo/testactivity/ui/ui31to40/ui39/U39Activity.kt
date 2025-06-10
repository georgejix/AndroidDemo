package com.jx.androiddemo.testactivity.ui.ui31to40.ui39

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.jx.androiddemo.R
import kotlinx.android.synthetic.main.activity_u39.img
import kotlinx.android.synthetic.main.activity_u39.root


class U39Activity : Activity() {
    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        updateTheme()
        setContentView(R.layout.activity_u39)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
        updateTheme2()
    }

    //关联?attr/资源
    private fun updateTheme() {
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
        }
    }

    //关联的是value-night value
    private fun updateTheme2() {
        if (resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        root.setBackgroundColor(getColor(R.color.u39_bg))
        img.setImageResource(R.mipmap.icon_hook)
    }

}