package com.example.myarchitecture.model.baseModels

import android.os.Parcel
import android.os.Parcelable

class MessageModel() : Parcelable {
    var key: String? = null
    var value: String? = null

    constructor(parcel: Parcel) : this() {
        key = parcel.readString()
        value = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageModel> {
        override fun createFromParcel(parcel: Parcel): MessageModel {
            return MessageModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageModel?> {
            return arrayOfNulls(size)
        }
    }
}