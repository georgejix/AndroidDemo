package com.jx.androiddemo.testactivity.function.f41to50.f45

import android.annotation.SuppressLint
import android.location.GnssStatus
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import java.util.concurrent.TimeUnit


class F45Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {
    private val TAG  = javaClass.simpleName
    private val mLocationManager: LocationManager by lazy {getSystemService(LOCATION_SERVICE) as LocationManager}
    private var mInit = false
    private val tv_start by lazy { findViewById<View>(R.id.tv_start) }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f45
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
        RxView.clicks(tv_start)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                start()
            }
    }

    @SuppressLint("NewApi", "MissingPermission")
    private fun start() {
        if(mInit) return
        mInit = true
        mLocationManager.registerGnssStatusCallback(
            object : GnssStatus.Callback(){
                override fun onSatelliteStatusChanged(status: GnssStatus) {
                    printGnss(status)
                }
            }, Handler(Looper.getMainLooper())
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun printGnss(status : GnssStatus){
        val count = status.satelliteCount
        Log.d(TAG, "count = $count")
        repeat(count){index->
            Log.d(TAG, "index = $index used = ${status.usedInFix(index)}")
        }
    }
}