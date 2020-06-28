package com.kostikum.kvartirka.entity

import android.os.Parcel
import android.os.Parcelable

data class Flat(
    val id: Int,
    val building_type: String,
    val rooms: Int,
    val address: String,
    val prices: Prices,
    val photo_default: PhotoDefault,
    val photos: List<Photo>
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Flat> {
            override fun createFromParcel(parcel: Parcel) = Flat(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Flat>(size)
        }
    }

    override fun describeContents() = 0

    constructor(parcel: Parcel) :
            this(
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readInt(),
                parcel.readString() ?: "",
                Prices(parcel.readInt(), parcel.readInt(), parcel.readInt()),
                PhotoDefault(parcel.readString() ?: ""),
                getListFromParcel(parcel)
            )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(building_type)
            writeInt(rooms)
            writeString(address)
            writeInt(prices.day)
            writeInt(prices.night)
            writeInt(prices.hour)
            writeString(photo_default.url)
            writeList(photos)
        }
    }
}

private fun getListFromParcel(parcel: Parcel): List<Photo> {
    val list: List<Photo> = emptyList()
    parcel.readList(list, Photo::class.java.classLoader)
    return list
}
