package com.example.myarchitecture.shared.data.networking

open class NetworkState(val status: Status, val msg: String?) {

    enum class Status {
        RUNNING,
        SUCCESS,
        UN_AUTHORIZATION_ERROR,
        NETWORK_ERROR,
        SERVER_ERROR,
        API_ERROR
    }

    companion object {
        var LOADED: NetworkState? = null
        var LOADING: NetworkState? = null
        var UN_AUTHORIZATION_ERROR: NetworkState? = null
        var NETWORK_ERROR: NetworkState? = null
        var SERVER_ERROR: NetworkState? = null
        var API_ERROR: NetworkState? = null

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            UN_AUTHORIZATION_ERROR = NetworkState(Status.UN_AUTHORIZATION_ERROR, "Un authorization error")
            NETWORK_ERROR = NetworkState(Status.NETWORK_ERROR, "Network error")
            SERVER_ERROR = NetworkState(Status.SERVER_ERROR, "Server error")
            API_ERROR = NetworkState(Status.API_ERROR, "Api error")
        }
    }
}