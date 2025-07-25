package com.jx.androiddemo.testactivity.function.f41to50.f44

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class F44Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private val tv_play by lazy { findViewById<View>(R.id.tv_play) }

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f44
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
        RxView.clicks(tv_play)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                play()
            }
    }

    @SuppressLint("NewApi")
    private fun play() {
        CoroutineScope(Dispatchers.IO).launch {
            val mediaPlayer = MediaPlayer()
            val fd = resources.openRawResourceFd(R.raw.wav_1khz)
            mediaPlayer.setOnPreparedListener {
                Log.d(TAG, "Prepared")
                mediaPlayer.start()
            }
            mediaPlayer.setOnCompletionListener {
                Log.d(TAG, "Completion")
                mediaPlayer.release()
                fd.close()
            }
            mediaPlayer.reset()
            mediaPlayer.setDataSource(fd)
            mediaPlayer.prepareAsync()
        }
    }
}