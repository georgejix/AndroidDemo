package com.jx.testtheme

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ViewModel2 : ViewModel() {
    private val TAG = javaClass.simpleName
    var mStr = ObservableField<String>("")
}