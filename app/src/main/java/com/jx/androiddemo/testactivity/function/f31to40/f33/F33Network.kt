package com.jx.androiddemo.testactivity.function.f31to40.f33

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class F33Network {
    private val mThreadPool by lazy { initThreadPool() }

    fun execute(runnable: Runnable) {
        mThreadPool.execute(runnable)
    }

    private fun initThreadPool(): ThreadPoolExecutor {
        val tp = ThreadPoolExecutor(3, 3 * 5,
            5, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(), object : ThreadFactory {
                var mCount = 0
                override fun newThread(r: Runnable?): Thread {
                    return Thread(r, "thread${mCount++}")
                }
            })
        tp.allowCoreThreadTimeOut(true)
        return tp
    }

    fun sendPost() {
        Thread.sleep(500)
        val json = JSONObject()
        json.put("source", "APP")
        json.put("topN", 10)
        val url = "https://172.16.17.21:8443/biz-api/data/backgroundAudioQuery"
        val mediaType = MediaType.parse("application/json;charset=utf-8")
        val requestBody = RequestBody.create(mediaType, json.toString())
        val request = Request.Builder()
            .url(url)
            .headers(buildHttpHeader())
            .post(requestBody)
            .build()
        val resp = buildHttpClient(5000).newCall(request).execute()
        if (200 == resp.code()) {
            Log.d(F33Activity.TAG, "${resp.body()?.string()}")
        }
    }

    private fun buildHttpHeader(): Headers {
        return Headers.Builder()
            .add("token", "44fe8519f6a94a0893716555b7a08516")
            .build()
    }

    private fun buildHttpClient(timeout: Int): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeout.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(timeout.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(timeout.toLong(), TimeUnit.MILLISECONDS)
            .sslSocketFactory(SSLSocketManager.getSSLSocketFactory()) //配置
            .hostnameVerifier(SSLSocketManager.getHostnameVerifier()) //配置
            .build()
    }
}