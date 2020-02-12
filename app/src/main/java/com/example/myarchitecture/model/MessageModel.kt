package com.example.myarchitecture.model

import android.os.Parcel
import android.os.Parcelable

class MessageModel : Parcelable {
    var key: String? = null
    var value: String? = null

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MessageModel> = object : Parcelable.Creator<MessageModel> {
            override fun createFromParcel(source: Parcel): MessageModel = MessageModel()
            override fun newArray(size: Int): Array<MessageModel?> = arrayOfNulls(size)
        }
    }
}