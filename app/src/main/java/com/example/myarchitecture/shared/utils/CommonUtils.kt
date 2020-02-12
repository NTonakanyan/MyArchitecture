package com.example.myarchitecture.shared.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Patterns

object CommonUtils {

    val mobileModel: String
        get() = String.format("%s %s", Build.MANUFACTURER, Build.MODEL)

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}