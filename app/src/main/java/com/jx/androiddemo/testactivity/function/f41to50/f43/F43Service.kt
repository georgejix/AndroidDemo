package com.jx.androiddemo.testactivity.function.f41to50.f43

import android.app.Service
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.os.IBinder
import android.os.ResultReceiver
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class F43Service : Service() {
    private val TAG = "F43Service"
    private val mMediaSession by lazy { MediaSession(this, "audio1") }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        initMediaSession()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun initMediaSession() {
        mMediaSession.setCallback(object : MediaSession.Callback() {
            override fun onPlay() {
                super.onPlay()
                Log.d(TAG, "play")
            }

            override fun onPause() {
                super.onPause()
                Log.d(TAG, "pause")
            }

            override fun onStop() {
                super.onStop()
                Log.d(TAG, "stop")
            }

            override fun onSkipToNext() {
                super.onSkipToNext()
                Log.d(TAG, "onSkipToNext")
            }

            override fun onSkipToPrevious() {
                super.onSkipToPrevious()
                Log.d(TAG, "onSkipToPrevious")
            }

            override fun onCommand(command: String, args: Bundle?, cb: ResultReceiver?) {
                super.onCommand(command, args, cb)
                Log.d(TAG, "oncommand ${command} key=${args?.getString("key")}")
                cb?.send(0, Bundle().apply {
                    putString("result", "ok")
                })
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            //设置歌曲名，专辑，演唱者，总时长
            val metadataBuilder = MediaMetadata.Builder()
            metadataBuilder.putString(MediaMetadata.METADATA_KEY_TITLE, "test_title")
            metadataBuilder.putString(MediaMetadata.METADATA_KEY_ALBUM, "test_album")
            metadataBuilder.putString(MediaMetadata.METADATA_KEY_ARTIST, "test_artist")
            metadataBuilder.putLong(MediaMetadata.METADATA_KEY_DURATION, 280)
            mMediaSession.setMetadata(metadataBuilder.build())

            //设置附加字段
            val extra = Bundle()
            extra.putString("mode", "fm")
            mMediaSession.setExtras(extra)

            //设置播放状态，当前时长
            val playStateBuilder = PlaybackState.Builder()
            playStateBuilder.setState(PlaybackState.STATE_PAUSED, 80, 1.0f)
            playStateBuilder.setBufferedPosition(60)
            mMediaSession.setPlaybackState(playStateBuilder.build())

            //设置激活状态
            mMediaSession.isActive = true
        }
    }
}