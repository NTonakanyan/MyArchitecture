package com.example.myarchitecture.shared.data.networking

import com.example.myarchitecture.App

class ExceptionHandler {
    lateinit var mIExceptionHandler: IExceptionHandler

    fun setOnExceptionHandler(iExceptionHandler: IExceptionHandler) {
        mIExceptionHandler = iExceptionHandler
    }

    fun onError(networkState: NetworkState?) {
        if (networkState == NetworkState.UN_AUTHORIZATION_ERROR) {
            App.instance.unAuthorization()
            return
        }
        mIExceptionHandler.onError(networkState)
    }

    interface IExceptionHandler {
        fun onError(networkState: NetworkState?)
    }
}