package com.jx.testtheme

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.jx.testtheme1.R

//手动更换主题颜色
class TestTheme2Activity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_test2)
        updateTheme()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        updateTheme()
    }

    private fun updateTheme() {
        Log.d(TAG, "updateTheme ${resources.configuration.uiMode}")
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
            mBg.setBackgroundColor(Color.BLACK)
            mTv.setTextColor(Color.WHITE)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
            mBg.setBackgroundColor(Color.WHITE)
            mTv.setTextColor(Color.BLACK)
        }
    }
}
