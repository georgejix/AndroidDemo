package com.jx.kt

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

class 测试协程 {

    private var mJob1 : Job?=null
    @Test
    fun test(){
        println("start job1")
        mJob1 = CoroutineScope(Dispatchers.IO).launch {
            println("job1")
            delay(5000)
            println("job1")
        }
        println("cancel job1")
        mJob1?.cancel()
        mJob1 = CoroutineScope(Dispatchers.IO).launch {
            println("job2")
            delay(5000)
            println("job2")
        }
        Thread.sleep(5000)
        println("end")
    }
}