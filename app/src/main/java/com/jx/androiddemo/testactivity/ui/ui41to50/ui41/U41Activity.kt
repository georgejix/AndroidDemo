package com.jx.androiddemo.testactivity.ui.ui41to50.ui41

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.jx.androiddemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class U41Activity : Activity() {
    private val TAG = this::class.java.simpleName
    private val mImg: ImageView by lazy { findViewById(R.id.img) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_u41)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(this@U41Activity)
                .load(R.mipmap.pic4)
                .apply(
                    RequestOptions()
                        .downsample(DownsampleStrategy.AT_MOST) // 关键：限制最大尺寸
                        .override(2048, 2048) // 设置最大允许尺寸
                        .format(DecodeFormat.PREFER_RGB_565) // 减少内存占用
                ).into(mImg)
        }
    }

}