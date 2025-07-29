package com.jx.testtheme_base

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class Fragment1() : Fragment() {
    private val TAG = javaClass.simpleName
    private var mRootView: View? = null
    private var mStr: String? = null
    private var mTv: TextView? = null
    private var mBg: View? = null

    init {
        Log.d(TAG, "init $mStr")
    }

    fun setText(str: String) {
        mStr = str
        mTv?.text = str
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate $mStr")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onViewCreated $mStr")
        mRootView = inflater.inflate(R.layout.fragment_fragment1, null)
        mBg = mRootView?.findViewById(R.id.bg)
        mTv = mRootView?.findViewById(R.id.tv)
        mTv?.text = mStr ?: "empty"
        updateTheme()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated $mStr")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume $mStr")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy $mStr")
    }

    fun updateTheme() {
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式 $mStr")
            mBg?.setBackgroundColor(Color.BLACK)
            mTv?.setTextColor(Color.WHITE)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式 $mStr")
            mBg?.setBackgroundColor(Color.WHITE)
            mTv?.setTextColor(Color.BLACK)
        }
    }
}