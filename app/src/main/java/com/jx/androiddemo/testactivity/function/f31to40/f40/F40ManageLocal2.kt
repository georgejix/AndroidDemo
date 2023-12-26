package com.jx.androiddemo.testactivity.function.f31to40.f40

import android.os.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface F40ManageLocal2 : IInterface {
    abstract class Stub : Binder(), F40ManageLocal2 {
        override fun asBinder(): IBinder {
            return this
        }

        @Throws(RemoteException::class)
        public override fun onTransact(
            code: Int,
            data: Parcel,
            reply: Parcel?,
            flags: Int
        ): Boolean {
            val descriptor = DESCRIPTOR
            return when (code) {
                INTERFACE_TRANSACTION -> {
                    reply!!.writeString(descriptor)
                    true
                }
                TRANSACTION_basicTypes -> {
                    data.enforceInterface(descriptor)
                    val _arg0: Int
                    _arg0 = data.readInt()
                    val _arg1: Long
                    _arg1 = data.readLong()
                    val _arg2: Boolean
                    _arg2 = 0 != data.readInt()
                    val _arg3: Float
                    _arg3 = data.readFloat()
                    val _arg4: Double
                    _arg4 = data.readDouble()
                    val _arg5: String?
                    _arg5 = data.readString()
                    basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5)
                    reply!!.writeNoException()
                    true
                }
                TRANSACTION_sendMsg -> {
                    data.enforceInterface(descriptor)
                    val _arg0: String?
                    _arg0 = data.readString()
                    sendMsg(_arg0)
                    reply!!.writeNoException()
                    true
                }
                else -> {
                    super.onTransact(code, data, reply, flags)
                }
            }
        }

        private class Proxy internal constructor(private val mRemote: IBinder) : F40ManageLocal2 {
            override fun asBinder(): IBinder {
                return mRemote
            }

            /**
             * Demonstrates some basic types that you can use as parameters
             * and return values in AIDL.
             */
            override fun basicTypes(
                anInt: Int,
                aLong: Long,
                aBoolean: Boolean,
                aFloat: Float,
                aDouble: Double,
                aString: String?
            ) {
                val _data = Parcel.obtain()
                val _reply = Parcel.obtain()
                try {
                    _data.writeInterfaceToken(DESCRIPTOR)
                    _data.writeInt(anInt)
                    _data.writeLong(aLong)
                    _data.writeInt(if (aBoolean) 1 else 0)
                    _data.writeFloat(aFloat)
                    _data.writeDouble(aDouble)
                    _data.writeString(aString)
                    val _status =
                        mRemote.transact(
                            TRANSACTION_basicTypes,
                            _data,
                            _reply,
                            0
                        )
                    if (!_status && defaultImpl != null) {
                        defaultImpl!!.basicTypes(anInt, aLong, aBoolean, aFloat, aDouble, aString)
                        return
                    }
                    _reply.readException()
                } finally {
                    _reply.recycle()
                    _data.recycle()
                }
            }

            override fun sendMsg(msg: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val _data = Parcel.obtain()
                    val _reply = Parcel.obtain()
                    try {
                        _data.writeInterfaceToken(DESCRIPTOR)
                        _data.writeString(msg)
                        val _status =
                            mRemote.transact(TRANSACTION_sendMsg, _data, _reply, 0)
                        if (!_status && defaultImpl != null) {
                            defaultImpl!!.sendMsg(msg)
                        } else {
                            _reply.readException()
                        }
                    } finally {
                        _reply.recycle()
                        _data.recycle()
                    }
                }
            }

            companion object {
                var sDefaultImpl: F40ManageLocal2? = null
            }
        }

        /** Construct the stub at attach it to the interface.  */
        init {
            attachInterface(this, DESCRIPTOR)
        }

        companion object {
            val DESCRIPTOR = "com.jx.androiddemo.aidl2.F40ManageLocal2"

            /**
             * Cast an IBinder object into an com.jx.androiddemo.aidl2.F40ManageLocal2 interface,
             * generating a proxy if needed.
             */
            fun asInterface(obj: IBinder?): F40ManageLocal2? {
                if (obj == null) {
                    return null
                }
                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is F40ManageLocal2) {
                    iin
                } else Proxy(obj)
            }

            const val TRANSACTION_basicTypes = FIRST_CALL_TRANSACTION + 0
            const val TRANSACTION_sendMsg = FIRST_CALL_TRANSACTION + 1
            fun setDefaultImpl(impl: F40ManageLocal2?): Boolean {
                // Only one user of this interface can use this function
                // at a time. This is a heuristic to detect if two different
                // users in the same process use this function.
                check(Proxy.sDefaultImpl == null) { "setDefaultImpl() called twice" }
                if (impl != null) {
                    Proxy.sDefaultImpl = impl
                    return true
                }
                return false
            }

            val defaultImpl: F40ManageLocal2?
                get() = Proxy.sDefaultImpl
        }
    }

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    open fun basicTypes(
        anInt: Int,
        aLong: Long,
        aBoolean: Boolean,
        aFloat: Float,
        aDouble: Double,
        aString: String?
    )

    open fun sendMsg(msg: String?)
}
