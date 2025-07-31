package com.jx.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace
import com.jx.testtheme4.R
import com.jx.testtheme_base.Fragment1

//手动更换主题颜色
class TestTheme2Activity : FragmentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }
    private val mFragmentList: ArrayList<Fragment1> by lazy {
        Log.d(TAG, "init mFragmentList")
        arrayListOf(
            Fragment1().apply { setText("f1") },
            Fragment1().apply { setText("f2") },
            Fragment1().apply { setText("f3") },
        )
    }
    private var mNeedInitView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        updateTheme()
        setContentView(R.layout.activity_theme_test2)
        mNeedInitView = true
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        if (mNeedInitView) {
            initView()
            mNeedInitView = false
        }
    }

    private fun initView() {
        Log.d(TAG, "mFragmentList[0].hashCode=${mFragmentList[0].hashCode()}")
        supportFragmentManager.beginTransaction().replace(R.id.fl, mFragmentList[0]).commit()
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
