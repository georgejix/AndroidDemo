package com.jx.androiddemo.testactivity.function.f31to40.f38

import android.util.Log
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.resume

class F38Util {
    val TAG = "F38Util"
    val testRunBlocking = {
        //launch，不等待结果
        //runBlocking，等待里面所有launch结束才完成
        Log.d(TAG, "testRunBlocking ${Thread.currentThread().name} start")
        runBlocking {
            launch(Dispatchers.IO) {
                Log.d(TAG, "launch1 ${Thread.currentThread().name} start")
                delay(1000)
                Log.d(TAG, "launch1 ${Thread.currentThread().name} end")
            }
            launch(Dispatchers.IO) {
                Log.d(TAG, "launch2 ${Thread.currentThread().name} start")
                delay(500)
                Log.d(TAG, "launch2 ${Thread.currentThread().name} end")
            }
        }
        Log.d(TAG, "testRunBlocking ${Thread.currentThread().name} end")
    }

    val testLaunch = {
        Log.d(TAG, "testLaunch ${Thread.currentThread().name} start")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "launch1 ${Thread.currentThread().name} start")
            delay(1000)
            Log.d(TAG, "launch1 ${Thread.currentThread().name} end")
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "launch2 ${Thread.currentThread().name} start")
            delay(500)
            Log.d(TAG, "launch2 ${Thread.currentThread().name} end")
        }
        Log.d(TAG, "testLaunch ${Thread.currentThread().name} end")
    }

    val testAsync = {
        //async，返回结果，用于同步多个异步线程
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "testAsync start ${Thread.currentThread().name}")
            val async1 = async(Dispatchers.IO) {
                Log.d(TAG, "num1 ${Thread.currentThread().name}")
                delay(1000)
                100
            }
            val async2 = async(Dispatchers.IO) {
                Log.d(TAG, "num2 ${Thread.currentThread().name}")
                delay(1000)
                200
            }
            Log.d(TAG, "testAsync end ${Thread.currentThread().name}")
            val num = async1.await() + async2.await()
            Log.d(TAG, "num = $num ${Thread.currentThread().name}")
        }
    }

    val testWithContext = {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "testWithContext start ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                Log.d(TAG, "launch1 ${Thread.currentThread().name} start")
                delay(1000)
                Log.d(TAG, "launch1 ${Thread.currentThread().name} end")
            }
            Log.d(TAG, "testWithContext mid ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                Log.d(TAG, "launch2 ${Thread.currentThread().name} start")
                delay(500)
                Log.d(TAG, "launch2 ${Thread.currentThread().name} end")
            }
            Log.d(TAG, "testWithContext end ${Thread.currentThread().name}")
        }
    }

    val testSuspend = {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "testSuspend start ${Thread.currentThread().name}")
            val deferred1 = CoroutineScope(Dispatchers.IO).async {
                blocking(1)
                1
            }
            val deferred2 = CoroutineScope(Dispatchers.IO).async {
                blocking(2)
                2
            }
            val deferred3 = CoroutineScope(Dispatchers.IO).async {
                blocking(3)
                3
            }
            Log.d(TAG, "testSuspend end ${Thread.currentThread().name}")
            Log.d(TAG, "${deferred1.await() + deferred2.await() + deferred3.await()}")
        }
    }

    suspend fun blocking(param: Int) {
        Log.d(TAG, "enter blocking() with $param")
        delay(5000)
        Log.d(TAG, "done blocking() with $param")
    }

    val cbToSync = {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "cbToSync")
            val str = getStringSync()
            Log.d(TAG, "cbToSync result ${str}")
        }
    }

    private fun getStringAsync(callback: (String) -> Unit) {
        Log.d(TAG, "getStringAsync")
        // 执行一个调用回调的异步操作
        // 模拟延迟
        Thread.sleep((Math.random() * 2000).toLong())
        callback("Callback result")
    }

    private suspend fun getStringSync(): String {
        Log.d(TAG, "getStringSync")
        /*return suspendCoroutine { continuation ->
            // 执行一个调用回调的异步操作
            Log.d(TAG, "getStringSync suspendCoroutine")
            val timer = Timer("CoroutineTimer", false)
            timer.schedule(object : TimerTask() {
                override fun run() {
                    continuation.resume("Timeout occurred")
                }
                // 用超时结果恢复协程
            }, 1000L)
            getStringAsync { result ->
                // 用回调结果恢复协程
                Log.d(TAG, "getStringSync suspendCoroutine resume")
                kotlin.runCatching {
                    timer.cancel()
                    continuation.resume(result)
                }
            }
        }*/
        return suspendCancellableCoroutine { continuation ->
            // 执行一个调用回调的异步操作
            Log.d(TAG, "getStringSync suspendCoroutine")
            val timer = Timer("CoroutineTimer", false)
            timer.schedule(object : TimerTask() {
                override fun run() {
                    Log.d(TAG, "getStringSync time out")
                    continuation.cancel()
                }
                // 用超时结果恢复协程
            }, 1000L)
            getStringAsync { result ->
                // 用回调结果恢复协程
                Log.d(TAG, "getStringSync suspendCoroutine resume")
                if (!continuation.isCancelled) {
                    timer.cancel()
                    continuation.resume(result)
                } else {
                    Log.d(TAG, "getStringSync suspendCoroutine time out")
                }
            }
        }
    }

    fun testLaunchAsyncWithContext() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "testLaunchAsyncWithContext start ${Thread.currentThread().name}")
            val async = async {
                delayFunc()
            }
            Log.d(TAG, "testLaunchAsyncWithContext mid ${Thread.currentThread().name}")
            async.await()
            Log.d(TAG, "testLaunchAsyncWithContext end ${Thread.currentThread().name}")
        }
    }

    private suspend fun delayFunc() = withContext(Dispatchers.IO) {
        delay(2000)
        Result.success("success")
        Log.d(TAG, "delayFunc end ${Thread.currentThread().name}")
    }

}