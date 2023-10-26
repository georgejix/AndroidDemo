package com.jx.androiddemo.testactivity.function.f31to40.f38;

import android.annotation.SuppressLint
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.android.synthetic.main.activity_f38.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class F38Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    companion object {
        val TAG0 = "F38Activity"
    }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f38
    }

    @SuppressLint("CheckResult")
    @Override
    override fun initEventAndData() {
        initView()
        initListener()
    }

    private fun initView() {
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        RxView.clicks(tv_test1)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                //launch，不等待结果
                //runBlocking，等待里面所有launch结束才完成
                Log.d(TAG, "click ${Thread.currentThread().name} 1")
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
                Log.d(TAG, "launch ${Thread.currentThread().name} 2")
            }
        RxView.clicks(tv_test2)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                //async，返回结果，用于同步多个异步线程
                CoroutineScope(Dispatchers.Main).launch {
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
                    val num = async1.await() + async2.await()
                    Log.d(TAG, "num = $num ${Thread.currentThread().name}")
                }
            }
        RxView.clicks(tv_test3)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                runBlocking { separateAsync() }
            }
    }

    suspend fun blocking(param: Int) {
        Log.d(TAG, "enter blocking() with $param")
        delay(5000)
        Log.d(TAG, "done blocking() with $param")
    }

    suspend fun separateAsync() {
        Log.d(TAG, "separateAsync()")
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
        Log.d(TAG, "${deferred1.await() + deferred2.await() + deferred3.await()}")
    }
}