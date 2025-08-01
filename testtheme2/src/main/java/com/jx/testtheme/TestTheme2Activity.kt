package com.jx.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.jx.testtheme2.R

//手动更换主题颜色
class TestTheme2Activity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        updateTheme()
        setContentView(R.layout.activity_theme_test2)
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        mTv.text = "page2 ${hashCode()}"
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        updateTheme()
        setContentView(R.layout.activity_theme_test2)
    }

    private fun updateTheme() {
        Log.d(TAG, "updateTheme ${resources.configuration.uiMode}")
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
            setTheme(R.style.Theme_Night)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
            setTheme(R.style.Theme_Day)
        }
    }
}
