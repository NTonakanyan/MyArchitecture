package com.example.myarchitecture.shared.data.networking

import com.example.myarchitecture.App

class ExceptionHandler {
    lateinit var mIExceptionHandler: IExceptionHandler

    fun setOnExceptionHandler(iExceptionHandler: IExceptionHandler) {
        mIExceptionHandler = iExceptionHandler
    }

    fun onError(requestState: RequestState?) {
        if (requestState == RequestState.createRequestState(RequestState.Status.UN_AUTHORIZATION_ERROR)) {
            App.instance.unAuthorization()
            return
        }
        mIExceptionHandler.onError(requestState)
    }

    interface IExceptionHandler {
        fun onError(requestState: RequestState?)
    }
}