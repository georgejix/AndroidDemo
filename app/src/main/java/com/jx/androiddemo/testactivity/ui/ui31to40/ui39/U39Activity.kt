package com.jx.androiddemo.testactivity.ui.ui31to40.ui39

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.jx.androiddemo.R


class U39Activity : Activity() {
    private val TAG = this::class.java.simpleName
    private val img by lazy { findViewById<ImageView>(R.id.img) }
    private val root by lazy { findViewById<LinearLayout>(R.id.root) }

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

    @RequiresApi(Build.VERSION_CODES.M)
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
    @RequiresApi(Build.VERSION_CODES.M)
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