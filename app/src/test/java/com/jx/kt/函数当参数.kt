package com.jx.kt

import android.util.Log
import org.junit.Test

class 函数当参数 {
    @Test
    fun main() {
        println("函数当参数")
        test { say() }
        test { run() }
    }

    private fun test(func: Dog.() -> Unit) {
        var dog = Dog()
        dog.func()
    }
}


class Dog {
    fun say() {
        println("Dog say")
    }
    fun run(){
        println("Dog run")
    }
}

