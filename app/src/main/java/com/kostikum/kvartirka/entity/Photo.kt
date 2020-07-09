package com.kostikum.kvartirka.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(val url: String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Photo> {
            override fun createFromParcel(parcel: Parcel) = Photo(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Photo>(size)
        }
    }

    override fun describeContents() = 0

    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(url)
        }
    }
}
