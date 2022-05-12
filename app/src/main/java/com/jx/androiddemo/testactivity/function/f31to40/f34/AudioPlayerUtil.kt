package com.jx.androiddemo.testactivity.function.f31to40.f34

import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jx.androiddemo.R
import java.util.concurrent.atomic.AtomicBoolean

class AudioPlayerUtil(val mLooper: Looper, val lifecycle: Lifecycle) {
    private val TAG = "AudioPlayerUtil"
    private val mPlayer: MediaPlayer = MediaPlayer()
    private val mIsPlaying = AtomicBoolean(false)
    private var mHandler: Handler
    private val REFRESH_TIME = 1001
    private var mNeedReset = true

    var mSeekBar: SeekBar? = null
    var mCurTimeTv: TextView? = null
    var mTotalTimeTv: TextView? = null
    var mPlayImg: ImageView? = null
    var mPath: String? = null

    init {
        mHandler = object : Handler(mLooper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    REFRESH_TIME -> {
                        //刷新当前播放时间和进度条
                        mCurTimeTv?.text = mPlayer.currentPosition.toString()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mSeekBar?.setProgress(mPlayer.currentPosition, true)
                        } else {
                            mSeekBar?.progress = mPlayer.currentPosition
                        }
                        sendEmptyMessageDelayed(REFRESH_TIME, 300)
                    }
                }
            }
        }
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            private fun pause() {
                mPlayer.pause()
                playStatusChange(false)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            private fun destroy() {
                playStatusChange(false)
                mPlayer.release()
            }
        })
    }

    fun initPlayer() {
        mPlayer.setOnPreparedListener { mp: MediaPlayer? ->
            Log.d(TAG, "准备好了")
            mPlayer.start()
            mHandler.post {
                playStatusChange(true)
                mTotalTimeTv?.text = mPlayer.duration.toString()
                mSeekBar?.max = mPlayer.duration
            }
        }
        mPlayer.setOnCompletionListener { mp: MediaPlayer? ->
            if (mPlayer.currentPosition == mPlayer.duration) {
                Log.d(TAG, "播放完成")
                mPlayer.reset()
                mHandler.post {
                    playStatusChange(false)
                    mCurTimeTv?.text = "0"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mSeekBar?.setProgress(0, true)
                    } else {
                        mSeekBar?.progress = 0
                    }
                }
            }
        }
        mSeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mPlayer.pause()
                playStatusChange(false)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                playPause(seekBar.progress)
            }
        })
        mPlayImg?.setOnClickListener { playPause(null) }
    }

    fun resetPath(path: String?) {
        mPath = path
        mCurTimeTv?.text = "0"
        mTotalTimeTv?.text = "0"
        mSeekBar?.max = 0
        mSeekBar?.progress = 0
        mNeedReset = true
        mPlayer.pause()
        playStatusChange(false)
    }

    //播放暂停
    fun playPause(seekPosition: Int?) {
        if (0 == mPlayer.duration || mNeedReset) {
            //未加载数据
            try {
                mPath?.apply {
                    mPlayer.reset()
                    mPlayer.setDataSource(this)
                    mPlayer.prepare()
                }
            } catch (e: Exception) {
            }
            mNeedReset = false
        } else {
            if (!mIsPlaying.get()) {
                //暂停中
                mPlayer.start()
                playStatusChange(true)
            } else if (mIsPlaying.get()) {
                //播放中
                seekPosition ?: let {
                    mPlayer.pause()
                    playStatusChange(false)
                }
            }
        }
        seekPosition?.let { mPlayer.seekTo(if (mPlayer.duration - it < 1000) mPlayer.duration else it) }
    }


    //更新播放暂停状态
    private fun playStatusChange(isPlay: Boolean) {
        mIsPlaying.set(isPlay)
        mHandler.post {
            mPlayImg?.setImageResource(if (isPlay) R.mipmap.img_audio_pause else R.mipmap.img_audio_play)
        }
        mHandler.removeMessages(REFRESH_TIME)
        if (isPlay) {
            mHandler.sendEmptyMessageDelayed(REFRESH_TIME, 300)
        }
    }

}