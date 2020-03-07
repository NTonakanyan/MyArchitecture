package com.example.myarchitecture.shared.data.networking

open class RequestState(val isMainRequest: Boolean = false, val status: Status, val msg: String?) {

    enum class Status {
        NETWORK_ERROR,
        SERVER_ERROR,
        API_ERROR,
        EMPTY,
        SUCCESS,
        LOADING
    }

    companion object {
        fun createRequestState(status: Status,isMainRequest: Boolean = false): RequestState {
            return when (status) {
                Status.NETWORK_ERROR -> RequestState(isMainRequest, Status.NETWORK_ERROR, "Network error")
                Status.SERVER_ERROR -> RequestState(isMainRequest, Status.SERVER_ERROR, "Server error")
                Status.API_ERROR -> RequestState(isMainRequest, Status.API_ERROR, "Api error")
                Status.EMPTY -> RequestState(isMainRequest, Status.EMPTY, "Empty")
                Status.SUCCESS -> RequestState(isMainRequest, Status.SUCCESS, "Success")
                Status.LOADING -> RequestState(isMainRequest, Status.LOADING, "Loading")
            }
        }
    }
}