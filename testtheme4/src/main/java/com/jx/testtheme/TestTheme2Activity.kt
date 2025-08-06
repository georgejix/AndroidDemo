package com.jx.testtheme

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.jx.testtheme4.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//手动更换主题颜色
class TestTheme2Activity : FragmentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }
    private val mCb by lazy { findViewById<CheckBox>(R.id.cb) }
    private val mTvRR by lazy { findViewById<TextView>(R.id.tv_rr) }
    private val mTvRS by lazy { findViewById<TextView>(R.id.tv_rs) }
    private val mTvSS by lazy { findViewById<TextView>(R.id.tv_ss) }
    private val mTvSR by lazy { findViewById<TextView>(R.id.tv_sr) }
    private var mJob: Job? = null

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
        addListener()
    }

    private fun addListener() {
        listOf(mTvRR, mTvRS, mTvSS, mTvSR).forEach {
            it.setOnClickListener { loadFragment(mCb.isChecked, it.id) }
        }
    }

    override fun onStop() {
        super.onStop()
        mJob?.cancel()
    }

    private fun printPopStack() {
        runCatching {
            //禁止加入栈
            val field = Class.forName("androidx.fragment.app.FragmentManager").declaredFields.find {
                it.name == "mBackStack"
            }
            field?.isAccessible = true
            val list = field?.get(supportFragmentManager) as ArrayList<Any>
            Log.d(TAG, "mBackStack size: ${list.size}")
            list.forEachIndexed { index, it ->
                Log.d(
                    TAG,
                    "mBackStack $index: ${it.javaClass.name}"
                )
            }
        }
    }

    private fun clearPopStack() {
        runCatching {
            //禁止加入栈
            val field = Class.forName("androidx.fragment.app.FragmentManager").declaredFields.find {
                it.name == "mBackStack"
            }
            field?.isAccessible = true
            val list = field?.get(supportFragmentManager) as ArrayList<Any>
            list.clear()
        }
    }

    /**
     * hide show方式加载的fragment不会在隐藏时销毁，切换主题后全部会被调用onCreate,onResume
     * replace方式加载（不addToBackStack入栈）的fragment在被replace后销毁
     * replace方式加载（addToBackStack入栈）的fragment在被replace不销毁，切换主题后会被调用onCreate
     */
    private fun loadFragment(addBack: Boolean, id: Int) {
        Log.d(TAG, "loadFragment ${supportFragmentManager.fragments.size}")
        //clearPopStack()
        //printPopStack()
        mJob?.cancel()
        when (id) {
            R.id.tv_rr -> {
                mJob = CoroutineScope(Dispatchers.Main).launch {
                    val f2_1 = Fragment2_1()
                    supportFragmentManager.beginTransaction().replace(R.id.fl, f2_1)
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_1())
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_2())
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)

                    val f2_2 = Fragment2_2()
                    supportFragmentManager.beginTransaction().replace(R.id.fl, f2_2)
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_3())
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_4())
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                }
            }

            R.id.tv_rs -> {
                mJob = CoroutineScope(Dispatchers.Main).launch {
                    val f2_1 = Fragment2_1()
                    supportFragmentManager.beginTransaction().replace(R.id.fl, f2_1)
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    val f3_1 = Fragment3_1()
                    val f3_2 = Fragment3_2()
                    f2_1.childFragmentManager.beginTransaction()
                        .add(R.id.fl2, f3_1).add(R.id.fl2, f3_2)
                        .show(f3_1).hide(f3_2).commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction()
                        .show(f3_2).hide(f3_1).commit()
                    delay(1000)
                    val f2_2 = Fragment2_2()
                    supportFragmentManager.beginTransaction().replace(R.id.fl, f2_2)
                        .also { if (addBack) it.addToBackStack(null) }.commit()
                    delay(1000)
                    val f3_3 = Fragment3_3()
                    val f3_4 = Fragment3_4()
                    f2_2.childFragmentManager.beginTransaction()
                        .add(R.id.fl2, f3_3).add(R.id.fl2, f3_4)
                        .show(f3_3).hide(f3_4).commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction()
                        .show(f3_4).hide(f3_3).commit()
                }
            }

            R.id.tv_ss -> {
                mJob = CoroutineScope(Dispatchers.Main).launch {
                    val f2_1 = Fragment2_1()
                    val f2_2 = Fragment2_2()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fl, f2_1).add(R.id.fl, f2_2)
                        .show(f2_1).hide(f2_2).commit()
                    delay(1000)
                    val f3_1 = Fragment3_1()
                    val f3_2 = Fragment3_2()
                    f2_1.childFragmentManager.beginTransaction()
                        .add(R.id.fl2, f3_1).add(R.id.fl2, f3_2)
                        .show(f3_1).hide(f3_2).commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction()
                        .show(f3_2).hide(f3_1).commit()
                    delay(1000)
                    supportFragmentManager.beginTransaction()
                        .show(f2_2).hide(f2_1).commit()
                    delay(1000)
                    val f3_3 = Fragment3_3()
                    val f3_4 = Fragment3_4()
                    f2_2.childFragmentManager.beginTransaction()
                        .add(R.id.fl2, f3_3).add(R.id.fl2, f3_4)
                        .show(f3_3).hide(f3_4).commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction()
                        .show(f3_4).hide(f3_3).commit()
                }
            }

            R.id.tv_sr -> {
                mJob = CoroutineScope(Dispatchers.Main).launch {
                    val f2_1 = Fragment2_1()
                    val f2_2 = Fragment2_2()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fl, f2_1).add(R.id.fl, f2_2)
                        .show(f2_1).hide(f2_2).commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_1())
                        .also { if (addBack) it.addToBackStack(null) }
                        .commit()
                    delay(1000)
                    f2_1.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_2())
                        .also { if (addBack) it.addToBackStack(null) }
                        .commit()
                    delay(1000)
                    supportFragmentManager.beginTransaction()
                        .show(f2_2).hide(f2_1).commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_3())
                        .also { if (addBack) it.addToBackStack(null) }
                        .commit()
                    delay(1000)
                    f2_2.childFragmentManager.beginTransaction().replace(R.id.fl2, Fragment3_4())
                        .also { if (addBack) it.addToBackStack(null) }
                        .commit()
                }
            }
        }
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
