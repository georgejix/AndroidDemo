package com.jx.androiddemo.testactivity.ui.ui31to40.ui40

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.jx.androiddemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class U40Activity : Activity() {
    private val TAG = this::class.java.simpleName
    private val img by lazy { findViewById<ImageView>(R.id.img) }
    private val mSdf by lazy { SimpleDateFormat("yyyy-MM-dd mm:hh:ss") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_u40)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        CoroutineScope(Dispatchers.Main).launch {
            while (!isDestroyed) {
                img.setImageBitmap(genBitmap())
                delay(1000)
            }
        }
    }

    private fun genBitmap(): Bitmap {
        val paint = Paint()
        paint.setColor(resources.getColor(R.color.black))
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = resources.getDimension(R.dimen.pxtodp2)
        paint.textSize = resources.getDimension(R.dimen.pxtodp20)
        val bitmap = Bitmap.createBitmap(720, 1080, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawARGB(255, 0, 255, 0)
        canvas.drawText(mSdf.format(System.currentTimeMillis()), 100f, 100f, paint)
        return bitmap
    }
}