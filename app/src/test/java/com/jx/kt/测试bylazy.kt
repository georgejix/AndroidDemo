package com.jx.kt

import android.util.Log
import org.junit.Test

class 测试bylazy {

    private val aa by lazy {
       System.out.println("init aa")
        "aa"
    }

    @Test
    fun main() {
        aa
        System.out.println("get aa")
    }
}