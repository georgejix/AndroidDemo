package com.jx.testtheme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jx.testtheme4.databinding.FragmentFragment2Binding


class Fragment2() : Fragment() {
    private val TAG = javaClass.simpleName
    private var mBinding: FragmentFragment2Binding? = null
    var mStr: String? = null

    init {
        Log.d(TAG, "init $mStr")
    }

    fun setText(str: String) {
        mStr = str
        mBinding?.vm?.mStr?.set(str)
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
        return FragmentFragment2Binding.inflate(inflater, container, false).also {
            mBinding = it
            mBinding?.gvm = GlobalViewModel
            mBinding?.vm = ViewModelF1()
            mBinding?.vm?.mStr?.set(mStr)
        }.root
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
}