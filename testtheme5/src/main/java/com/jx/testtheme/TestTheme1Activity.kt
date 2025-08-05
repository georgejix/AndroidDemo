package com.jx.testtheme

import android.content.Intent
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jx.testtheme5.R
import com.jx.testtheme5.databinding.ActivityThemeTest1Binding
import com.jx.testtheme5.databinding.LayoutDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *   vp下缓存的fragment会被系统重建，丢失部分数据
 */
class TestTheme1Activity : FragmentActivity() {
    private val TAG = javaClass.simpleName
    private var mBinding: ActivityThemeTest1Binding? = null
    private var mNeedInitView = true
    private var mNeedAddListener = true
    private var mTest1Adapter: Test1Adapter? = null
    private val mAdapterData = arrayListOf<ItemBean>()
    private var mVpAdapter: FragmentStateAdapter? = null
    private var mStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_theme_test1)
        mBinding?.gvm = GlobalViewModel
        mNeedAddListener = true
        mNeedInitView = true
    }

    override fun onResume() {
        Log.d(TAG, "onResume $mStr")
        super.onResume()
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
    }

    private fun initView() {
        mBinding?.loading?.visibility = View.VISIBLE
        mBinding?.rv?.visibility = View.GONE
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            mBinding?.loading?.visibility = View.GONE
            mBinding?.rv?.visibility = View.VISIBLE
        }

        mBinding?.tv?.text = "page1 ${hashCode()}"
        mTest1Adapter ?: let {
            mTest1Adapter = Test1Adapter(this)
            repeat(5) {
                mAdapterData.add(ItemBean(resources.configuration.uiMode, "item$it$it$it"))
            }
        }
        mTest1Adapter?.setData(mAdapterData)
        mBinding?.rv?.adapter = mTest1Adapter

        mVpAdapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = 5

            override fun createFragment(position: Int): Fragment {
                Log.d(TAG, "createFragment $position")
                return Fragment2().apply { setText("fragment $position") }
            }

        }
        mBinding?.vp?.adapter = mVpAdapter
    }


    private fun addListener() {
        mBinding?.tv?.setOnClickListener {
            startActivity(Intent(this, TestTheme2Activity::class.java))
        }
        mBinding?.tvDialog?.setOnClickListener {
            /*val dialog = TipDialog(this@TestTheme1Activity)
            dialog.show()*/
            val view = LayoutDialogBinding.inflate(LayoutInflater.from(this), null, false)
            view.gvm = GlobalViewModel
            val params = WindowManager.LayoutParams(200, 200)
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION
            params.format = PixelFormat.TRANSLUCENT // 支持透明
            params.gravity = Gravity.CENTER
            windowManager.addView(view.root, params)
        }
    }
}
