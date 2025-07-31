package com.jx.testtheme_base

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class LocalLoading : SurfaceView, Runnable {

    private val TAG = "LocalLoading"
    private var mContext: Context? = null
    private var mDrawThread: Thread? = null
    private var mDrawing = false
    private val mBgPaint = Paint()
    private val mPaint = Paint()
    private var mAngle = 0
    private val mCircleCount = 6

    constructor(context: Context) : super(context, null) {
        mContext = context
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        mContext = context
    }

    init {
        mBgPaint.color = Color.WHITE
        mBgPaint.isAntiAlias = true

        mPaint.strokeWidth = 10f
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = Color.BLUE
        mPaint.isAntiAlias = true

        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceCreated")
                mDrawing = true
                mDrawThread = Thread(this@LocalLoading)
                mDrawThread?.start()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d(TAG, "surfaceDestroyed")
                mDrawing = false
                runCatching { mDrawThread?.join() }
            }
        })
    }

    override fun run() {
        Log.d(TAG, "LocalLoading start")
        while (mDrawing) {
            val canvas = holder.lockCanvas()
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                == Configuration.UI_MODE_NIGHT_YES
            ) {
                // 处于深色模式
                Log.d(TAG, "处于深色模式")
                mBgPaint.color = Color.BLACK
            } else {
                // 处于浅色模式
                Log.d(TAG, "处于浅色模式")
                mBgPaint.color = Color.WHITE
            }
            canvas.drawRect(Rect(0, 0, width, height), mBgPaint)
            //canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR)
            /*canvas.drawArc(
                20f, 20f, width - 40f, height - 40f,
                (mAngle++ * 30f) % 360, 270f, false, mPaint
            )*/
            repeat(mCircleCount) {
                canvas.save()
                mPaint.alpha = 200 * (it + 1) / mCircleCount + 55
                canvas.rotate(
                    (mAngle + it) * 300f / mCircleCount, width / 2f, height / 2f
                )
                canvas.drawCircle(width / 2f, height / 4f, width / 20f, mPaint)
                canvas.restore()
            }
            mAngle++
            holder.unlockCanvasAndPost(canvas)
            Thread.sleep(200)
        }
        Log.d(TAG, "LocalLoading exit")
    }
}