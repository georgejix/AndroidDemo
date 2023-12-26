package com.jx.androiddemo.testactivity.function.f31to40.f40

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.jx.androiddemo.aidl2.F40Manage

class F40Service : Service() {
    companion object {
        private val TAG = "F40ActivityService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        val f40Manage = object : F40Manage.Stub() {
            override fun basicTypes(
                anInt: Int,
                aLong: Long,
                aBoolean: Boolean,
                aFloat: Float,
                aDouble: Double,
                aString: String?
            ) {
            }

            override fun sendMsg(msg: String?) {
                Log.d(TAG,"receiveMsg $msg")
            }

        }
        return f40Manage.asBinder()
    }
}