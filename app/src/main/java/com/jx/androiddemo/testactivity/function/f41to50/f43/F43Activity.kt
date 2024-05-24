package com.jx.androiddemo.testactivity.function.f41to50.f43

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaSessionManager
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_f43.tv_control
import kotlinx.android.synthetic.main.activity_f43.tv_start
import kotlinx.android.synthetic.main.activity_f43.tv_print
import java.util.concurrent.TimeUnit


class F43Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private val mm by lazy { getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f43
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
        mm.addOnActiveSessionsChangedListener({ controllers ->
            Log.d(TAG, "onActiveSessionsChanged")
            controllers?.forEach {
                Log.d(TAG, "${it.packageName} ${it.flags}")
            }
        }, ComponentName(packageName, TAG))
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { id -> }

        //点击
        RxView.clicks(tv_start)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                start()
            }
        RxView.clicks(tv_print)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                print()
            }
        RxView.clicks(tv_control)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                control()
            }
    }

    private fun start() {
        startService(Intent(this, F43Service::class.java))
    }

    private fun print() {
        mm.getActiveSessions(ComponentName(packageName, TAG))
            .find { it.packageName == packageName }
            ?.let {
                Log.d(TAG, "title ${it.metadata?.description?.title}")
                Log.d(TAG, "album ${it.metadata?.getString(MediaMetadata.METADATA_KEY_ALBUM)}")
                Log.d(TAG, "artist ${it.metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST)}")
                Log.d(TAG, "duration ${it.metadata?.getLong(MediaMetadata.METADATA_KEY_DURATION)}")
                Log.d(TAG, "mode ${it.extras?.getString("mode")}")
                Log.d(TAG, "playstate ${it.playbackState?.toString()}")
            }
    }

    private fun control(){
        mm.getActiveSessions(ComponentName(packageName, TAG))
            .find { it.packageName == packageName }
            ?.let {
                it.transportControls.play()
                it.transportControls.pause()
                it.transportControls.stop()
                it.transportControls.skipToPrevious()
                it.transportControls.skipToNext()
            }
    }

}