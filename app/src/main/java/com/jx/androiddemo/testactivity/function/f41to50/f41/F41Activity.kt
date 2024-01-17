package com.jx.androiddemo.testactivity.function.f41to50.f41

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Parcel
import android.os.ParcelFileDescriptor
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_f40.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class F41Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    companion object {
        private val TAG0 = "F41Activity"
    }

    private var mFd: ParcelFileDescriptor? = null

    @Override
    override fun initInject() {
        activityComponent.inject(this)
    }

    @Override
    override fun getLayout(): Int {
        return R.layout.activity_f41
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
        RxView.clicks(tv1)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o -> saveParcel() }
        RxView.clicks(tv2)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { o -> getParcel() }
    }

    private fun saveParcel() {
        val list = ArrayList<UsbAudioInfo>()
        repeat(90000) { id ->
            list.add(
                UsbAudioInfo(
                    id.toLong(), id.toString(),
                    id.toString(), id.toLong(), id.toLong()
                )
            )
        }
        var outputStream: FileOutputStream? = null
        val path = "${cacheDir}${File.separator}parcel"
        val file = File(path)
        try {
            outputStream = FileOutputStream(file)
            list.forEachIndexed { index, usbAudioInfo ->
                val parcel = Parcel.obtain()
                if (0 == index) {
                    parcel.writeInt(list.size)
                }
                usbAudioInfo.writeToParcel(parcel, 0)
                parcel.setDataPosition(0)
                parcel.marshall()?.let {
                    outputStream.write(it, 0, it.size)
                }
                parcel.recycle()
            }
            Log.d(TAG0, "save success")
        } catch (e: java.lang.Exception) {
        } finally {
            outputStream?.close()
        }
        mFd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    private fun getParcel() {
        var inputStream: ParcelFileDescriptor.AutoCloseInputStream? = null
        try {
            inputStream = ParcelFileDescriptor.AutoCloseInputStream(mFd)
            var length = 0
            val bytesInit = ByteArray(2048)
            val bytesRead = ByteArray(1024)
            var parcel = Parcel.obtain()
            var size: Int = 0
            val list = ArrayList<UsbAudioInfo>()
            var terminal = false

            if (inputStream.read(bytesInit).also { length = it } > -1) {
                parcel.unmarshall(bytesInit, 0, length)
                parcel.setDataPosition(0)
                if (0 == size) {
                    size = parcel.readInt()
                }
                while (list.size < size) {
                    list.add(UsbAudioInfo.createFromParcel(parcel))
                    if (parcel.dataAvail() < 1024 && !terminal) {
                        val position = parcel.dataPosition() - 1024
                        if (inputStream.read(bytesRead).also { length = it } > -1) {
                            val bytesNew = ByteArray(2048)
                            var index = 0
                            parcel.setDataPosition(0)
                            val bytesOld = parcel.marshall()
                            for (i in 1024 until 2048) {
                                bytesNew[index++] = bytesOld[i]
                            }
                            for (i in 0 until length) {
                                bytesNew[index++] = bytesRead[i]
                            }
                            parcel.unmarshall(bytesNew, 0, index - 1)
                            parcel.setDataPosition(position)
                        } else {
                            terminal = true
                        }
                    }
                }
            }
            list.forEach {
                Log.d(TAG0, "${it?.id} ${it?.name} ${it?.path} ${it?.size} ${it?.duration}")
            }
            parcel.recycle()
            Log.d(TAG0,"get success")
        } catch (e: Exception) {
            Log.d(TAG0, "${e.message}")
        } finally {
            inputStream?.close()
        }
    }
}