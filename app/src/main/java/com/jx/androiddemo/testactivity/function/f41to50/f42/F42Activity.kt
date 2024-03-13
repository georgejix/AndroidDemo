package com.jx.androiddemo.testactivity.function.f41to50.f42

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.MediaRecorder
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_f42.tv_start
import kotlinx.android.synthetic.main.activity_f42.tv_stop
import java.io.File
import java.util.concurrent.TimeUnit


class F42Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private var mMediaRecorder: MediaRecorder? = null

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f42
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
        RxView.clicks(tv_stop)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o ->
                stop()
            }
    }

    private fun start() {
        mMediaRecorder = MediaRecorder()
        mMediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("/sdcard/Download/1.mp4")
            prepare()
            start()
        }
    }

    private fun stop() {
        mMediaRecorder?.stop()
        mMediaRecorder?.release()
        mMediaRecorder = null
    }

    fun getMicrophoneAvailable(context: Context): Boolean {
        val recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
        recorder.setOutputFile(File(context.cacheDir, "MediaUtil#micAvailTestFile").absolutePath)
        var available = true
        try {
            recorder.prepare()
            recorder.start()
        } catch (exception: Exception) {
            available = false
        }
        recorder.release()
        return available
    }
}