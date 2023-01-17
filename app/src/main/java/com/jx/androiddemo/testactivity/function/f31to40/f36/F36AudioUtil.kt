package com.jx.androiddemo.testactivity.function.f31to40.f36

import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.jx.androiddemo.tool.ToastUtil

class F36AudioUtil {
    companion object {
        val TAG = "F36AudioUtil"
    }

    val mPlayer: MediaPlayer by lazy { MediaPlayer() }
    var mSecondHandler: Handler? = null
    var mHandlerThread: HandlerThread? = null
    var mHandler: Handler? = null
    var mListener: Listener? = null
    private val REFRESH_TIME = 1001

    var mSeekBar: SeekBar? = null
        set(value) {
            if (value != field) {
                field?.progress = 0
            }
            field = value
        }
    var mCurTv: TextView? = null
        set(value) {
            if (value != field) {
                field?.text = "0"
            }
            field = value
        }
    var mTotalTv: TextView? = null
    var mPlayImg: ImageView? = null
        set(value) {
            if (value != field) {
                mListener?.onSetPlayImg(field, false)
            }
            field = value
        }
    var mAutoPlay = false
    private var mId: Int? = null
    private var mSeekProgress: Int? = null
    private var mPlayStatus: PlayStatus = PlayStatus.NOT_PREPARE
        set(value) {
            field = value
            when (value) {
                PlayStatus.STARTED -> {
                    mHandler?.post {
                        mListener?.onSetPlayImg(mPlayImg, true)
                    }
                    mSecondHandler?.removeMessages(REFRESH_TIME)
                    mSecondHandler?.sendEmptyMessageDelayed(REFRESH_TIME, 200)
                }
                PlayStatus.PAUSED -> {
                    mHandler?.post {
                        mListener?.onSetPlayImg(mPlayImg, false)
                    }
                    mSecondHandler?.removeMessages(REFRESH_TIME)
                }
                else -> {
                    if (PlayStatus.ERR == value) {
                        mSeekProgress = null
                    }
                    mSeekProgress ?: let {
                        mHandler?.post {
                            mCurTv?.text = "0"
                            mSeekBar?.progress = 0
                            mListener?.onSetPlayImg(mPlayImg, false)
                        }
                    }
                    mSecondHandler?.removeMessages(REFRESH_TIME)
                }
            }
        }

    enum class PlayStatus {
        NOT_PREPARE, PREPARED, STARTED, PAUSED, ERR, END
    }

    init {
        mPlayer.setOnPreparedListener {
            Log.d(TAG, "setOnPreparedListener")
            mPlayStatus = PlayStatus.PREPARED
            mHandler?.post {
                mCurTv?.text = mPlayer.currentPosition.toString()
                mTotalTv?.text = mPlayer.duration.toString()
            }
            if (mAutoPlay) {
                mSeekProgress?.let {
                    mPlayer.seekTo(it * mPlayer.duration / (mSeekBar?.max ?: 1))
                    mSeekProgress = null
                }
                playOrPause()
                mAutoPlay = false
            } else {

            }
        }
        mPlayer.setOnCompletionListener {
            Log.d(TAG, "setOnCompletionListener")
            mPlayStatus = PlayStatus.END
        }
        mPlayer.setOnErrorListener { mp, what, extra ->
            Log.d(TAG, "setOnErrorListener")
            mPlayStatus = PlayStatus.ERR
            true
        }
        mHandlerThread = HandlerThread("back")
        mHandlerThread?.apply {
            start()
            mSecondHandler = Handler(looper, object : Handler.Callback {
                override fun handleMessage(msg: Message): Boolean {
                    when (msg.what) {
                        REFRESH_TIME -> mHandler?.post {
                            mCurTv?.text = mPlayer.currentPosition.toString()
                            mSeekBar?.progress =
                                mPlayer.currentPosition * (mSeekBar?.max
                                    ?: 0) / mPlayer.duration
                            mSecondHandler?.removeMessages(REFRESH_TIME)
                            mSecondHandler?.sendEmptyMessageDelayed(REFRESH_TIME, 200)
                        }
                    }
                    return true
                }
            })
        }
        mHandler = Handler(Looper.getMainLooper())
    }

    fun setUrl(url: String?, id: Int? = null) {
        if (PlayStatus.STARTED == mPlayStatus) {
            mPlayer.pause()
            mPlayStatus = PlayStatus.PAUSED
        }
        mPlayStatus = PlayStatus.NOT_PREPARE
        id?.let { mId = it }
        url?.let {
            Log.d(TAG, "load $it")
            mSecondHandler?.post {
                mPlayer.reset()
                mPlayer.setDataSource(it)
                mPlayer.prepareAsync()
            }
        }
    }

    fun seek(progress: Int, url: String? = null, id: Int? = null) {
        id?.let {
            if (mId != id) {
                mPlayStatus = PlayStatus.NOT_PREPARE
                mSeekProgress = progress
                mAutoPlay = true
            }
            mId = it
        }
        when (mPlayStatus) {
            PlayStatus.STARTED -> {
                mPlayer.seekTo(progress * mPlayer.duration / (mSeekBar?.max ?: 1))
            }
            PlayStatus.PREPARED, PlayStatus.PAUSED, PlayStatus.END -> {
                mPlayer.seekTo(progress * mPlayer.duration / (mSeekBar?.max ?: 1))
                mPlayer.start()
                mPlayStatus = PlayStatus.STARTED
            }
            PlayStatus.NOT_PREPARE -> {
                mSeekProgress = progress
                mAutoPlay = true
                setUrl(url)
            }
            PlayStatus.ERR -> {
            }
        }
    }

    fun pause() {
        if (PlayStatus.STARTED == mPlayStatus) {
            playOrPause()
        }
    }

    fun playOrPause(url: String? = null, id: Int? = null) {
        id?.let {
            if (mId != id) {
                mPlayStatus = PlayStatus.NOT_PREPARE
                Log.d(TAG, "ttt, $mId $id")
                mAutoPlay = true
            }
            mId = it
        }
        when (mPlayStatus) {
            PlayStatus.NOT_PREPARE -> {
                setUrl(url)
            }
            PlayStatus.PREPARED -> {
                mPlayer.start()
                mPlayStatus = PlayStatus.STARTED
            }
            PlayStatus.STARTED -> {
                mPlayer.pause()
                mPlayStatus = PlayStatus.PAUSED
            }
            PlayStatus.PAUSED -> {
                mPlayer.start()
                mPlayStatus = PlayStatus.STARTED
            }
            PlayStatus.END -> {
                mPlayer.seekTo(0)
                mPlayer.start()
                mPlayStatus = PlayStatus.STARTED
            }
            PlayStatus.ERR -> {
                ToastUtil.showTextToast("error")
            }
        }
    }

    fun release() {
        mHandlerThread?.quitSafely()
        mPlayer.stop()
        mPlayer.release()
    }

    open class Listener {
        open fun onSetPlayImg(img: ImageView?, play: Boolean) {}
    }
}