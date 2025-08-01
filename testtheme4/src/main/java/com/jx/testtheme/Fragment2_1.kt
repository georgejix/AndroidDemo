package com.jx.testtheme

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jx.testtheme4.R


class Fragment2_1() : Fragment() {
    private val TAG = javaClass.simpleName
    private var mRootView: View? = null
    private var mTv: TextView? = null
    private var mBg: View? = null

    init {
        Log.d(TAG, "init hashCode=${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate ${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onViewCreated ${hashCode()}")
        mRootView = inflater.inflate(R.layout.fragment_fragment2, null)
        mBg = mRootView?.findViewById(R.id.bg)
        mTv = mRootView?.findViewById(R.id.tv)
        mTv?.text = "f2_1 ${hashCode()}"
        updateTheme()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ${hashCode()}")
    }

    fun updateTheme() {
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式 ${hashCode()}")
            mBg?.setBackgroundColor(Color.BLACK)
            mTv?.setTextColor(Color.WHITE)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式 ${hashCode()}")
            mBg?.setBackgroundColor(Color.WHITE)
            mTv?.setTextColor(Color.BLACK)
        }
    }
}