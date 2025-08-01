package com.jx.testtheme

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jx.testtheme2.R
import com.jx.testtheme_base.Fragment1
import com.jx.testtheme_base.LocalLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 通过setContentView重新初始化view
 */
class TestTheme1Activity : FragmentActivity() {
    private val TAG = javaClass.simpleName
    private var mTv: TextView? = null
    private var mLoading: LocalLoading? = null
    private var mRv: RecyclerView? = null
    private var mVp: ViewPager2? = null
    private var mNeedInitView = true
    private var mTest1Adapter: Test1Adapter? = null
    private val mAdapterData = arrayListOf<ItemBean>()
    private var mVpAdapter: FragmentStateAdapter? = null
    private var mStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        mNeedInitView = true
        updateTheme()
        setContentView(R.layout.activity_theme_test1)
    }

    override fun onResume() {
        Log.d(TAG, "onResume $mStr")
        super.onResume()
        if (mNeedInitView) {
            initView()
            mStr = hashCode().toString()
            mNeedInitView = false
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        updateTheme()
        setContentView(R.layout.activity_theme_test1)
        initView()
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

    private fun initView() {
        mTv = findViewById(R.id.tv)
        mRv = findViewById(R.id.rv)
        mLoading = findViewById(R.id.loading)
        mVp = findViewById(R.id.vp)
        mLoading?.visibility = View.VISIBLE
        mRv?.visibility = View.GONE
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            mLoading?.visibility = View.GONE
            mRv?.visibility = View.VISIBLE
        }

        mTv?.text = "page1 ${hashCode()}"
        mTest1Adapter ?: let {
            mTest1Adapter = Test1Adapter(this)
            repeat(5) {
                mAdapterData.add(ItemBean(resources.configuration.uiMode, "item$it$it$it"))
            }
        }
        mTest1Adapter?.setData(mAdapterData)
        mRv?.adapter = mTest1Adapter

        mVpAdapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = 5

            override fun createFragment(position: Int): Fragment {
                Log.d(TAG, "createFragment $position")
                return Fragment1().apply { setText("fragment $position") }
            }

        }
        mVp?.adapter = mVpAdapter

        addListener()
    }


    private fun addListener() {
        mTv?.setOnClickListener {
            startActivity(Intent(this, TestTheme2Activity::class.java))
        }
    }
}
