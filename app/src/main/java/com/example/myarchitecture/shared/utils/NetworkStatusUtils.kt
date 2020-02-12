package com.example.myarchitecture.shared.utils

import java.net.InetAddress

object NetworkStatusUtils {
    fun isNetworkAvailable(): Boolean {
        return try {
            val ipAddress = InetAddress.getByName("google.com")
            ipAddress.toString() != ""
        } catch (e: Exception) {
            false
        }
    }
}