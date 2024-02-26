package com.jx.kt

import org.junit.Test

class 测试type {
    private fun judgeType(key: Any?) {
        when (key) {
            is String? -> println("String?")
            is Int? -> println("Int?")
            is Boolean? -> println("Boolean?")
        }
    }

    @Test
    fun main() {
        val key: Int? = null
        judgeType(key)
    }
}