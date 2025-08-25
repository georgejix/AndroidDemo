package com.jx.testtheme6

import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import sample.ISample


class MainActivity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    private val mSurfaceView: SurfaceView by lazy { findViewById(R.id.sv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                bindAidl()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })
    }

    private fun bindAidl() {
        kotlin.runCatching {
            val getServiceMethod = Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("getService", *arrayOf<Class<*>>(String::class.java))
            val serviceManager = getServiceMethod.invoke(null, "sampleService")
            val sample = ISample.Stub.asInterface(serviceManager as IBinder)
            sample.setSurface(mSurfaceView.holder.surface)
            Log.d(TAG, "bindAidl end")
        }
    }
}