package com.example.myarchitecture.shared.data.networking

class RequestHandler {
    lateinit var mIExceptionHandler: IExceptionHandler

    fun setOnExceptionHandler(iExceptionHandler: IExceptionHandler) {
        mIExceptionHandler = iExceptionHandler
    }

    fun onError(requestState: RequestState?) {
        mIExceptionHandler.onError(requestState)
    }

    interface IExceptionHandler {
        fun onError(requestState: RequestState?)
    }
}