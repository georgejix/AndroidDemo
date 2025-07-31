package com.jx.testtheme

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jx.testtheme1.R
import com.jx.testtheme_base.Fragment1
import com.jx.testtheme_base.LocalLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 不重建对象，不会丢失数据
 */
class TestTheme1Activity : FragmentActivity() {
    private val TAG = javaClass.simpleName
    private val mBg by lazy { findViewById<View>(R.id.bg) }
    private val mTv by lazy { findViewById<TextView>(R.id.tv) }
    private val mLoading by lazy { findViewById<LocalLoading>(R.id.loading) }
    private val mRv by lazy { findViewById<RecyclerView>(R.id.rv) }
    private val mVp by lazy { findViewById<ViewPager2>(R.id.vp) }
    private var mNeedAddListener = true
    private var mNeedInitView = true
    private var mTest1Adapter: Test1Adapter? = null
    private val mAdapterData = arrayListOf<ItemBean>()
    private var mVpAdapter: FragmentStateAdapter? = null
    private var mStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onResume")
        mNeedAddListener = true
        mNeedInitView = true
        setContentView(R.layout.activity_theme_test1)
        updateTheme()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume $mStr")
        if (mNeedInitView) {
            initView()
            mStr = hashCode().toString()
            mNeedInitView = false
        }
        if (mNeedAddListener) {
            addListener()
            mNeedAddListener = false
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged ${newConfig.uiMode}")
        updateTheme()
    }

    private fun updateTheme() {
        Log.d(TAG, "updateTheme ${resources.configuration.uiMode}")
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            == Configuration.UI_MODE_NIGHT_YES) {
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
        mTest1Adapter?.let {
            mAdapterData.forEach { it.uiMode = resources.configuration.uiMode }
            it.notifyDataSetChanged()
        }
        supportFragmentManager.fragments.forEach {
            if (it is Fragment1) {
                it.updateTheme()
            }
        }
    }

    private fun initView() {
        mLoading.visibility = View.VISIBLE
        mRv.visibility = View.GONE
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            mLoading.visibility = View.GONE
            mRv.visibility = View.VISIBLE
        }

        mTest1Adapter = Test1Adapter(this)
        repeat(5) {
            mAdapterData.add(ItemBean(resources.configuration.uiMode, "item$it$it$it"))
        }
        mTest1Adapter?.setData(mAdapterData)
        mRv.adapter = mTest1Adapter

        mVpAdapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = 5

            override fun createFragment(position: Int): Fragment {
                Log.d(TAG, "createFragment $position")
                return Fragment1().apply { setText("fragment $position") }
            }

        }
        mVp.adapter = mVpAdapter
    }


    private fun addListener() {
        mTv.setOnClickListener {
            startActivity(Intent(this, TestTheme2Activity::class.java))
        }

    }
}
