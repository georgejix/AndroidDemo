package com.jx.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.jx.testtheme3.R

//手动更换主题颜色
class TestTheme2Activity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }
    private var mNeedRecreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        updateTheme()
        setContentView(R.layout.activity_theme_test2)
        mNeedRecreate = false
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        if (mNeedRecreate) return
        mTv.text = "page2 ${hashCode()}"
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        mNeedRecreate = true
        //recreate()
        mBg.setBackgroundColor(getColor(R.color.color_d_fff_n_000))
        mTv.setTextColor(getColor(R.color.color_d_000_n_fff))
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
