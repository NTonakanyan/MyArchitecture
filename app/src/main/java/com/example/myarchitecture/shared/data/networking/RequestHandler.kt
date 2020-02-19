package com.example.myarchitecture.shared.data.networking

class RequestHandler {
    lateinit var mIExceptionHandler: IExceptionHandler

    fun setOnExceptionHandler(iExceptionHandler: IExceptionHandler) {
        mIExceptionHandler = iExceptionHandler
    }

    fun postAction(requestState: RequestState?) {
        mIExceptionHandler.onChanged(requestState)
    }

    interface IExceptionHandler {
        fun onChanged(requestState: RequestState?)
    }
}