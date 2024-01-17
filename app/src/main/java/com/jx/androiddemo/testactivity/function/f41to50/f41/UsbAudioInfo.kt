package com.jx.androiddemo.testactivity.function.f41to50.f41

import android.os.Parcel
import android.os.Parcelable

data class UsbAudioInfo(
    val id: Long, val path: String, val name: String, val size: Long,
    val duration: Long
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(path)
        parcel.writeString(name)
        parcel.writeLong(size)
        parcel.writeLong(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsbAudioInfo> {
        override fun createFromParcel(parcel: Parcel): UsbAudioInfo {
            return UsbAudioInfo(parcel)
        }

        override fun newArray(size: Int): Array<UsbAudioInfo?> {
            return arrayOfNulls(size)
        }
    }
}
