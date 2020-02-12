package com.example.myarchitecture.shared.data.services.root

import com.example.myarchitecture.App
import com.example.myarchitecture.model.ResponseModel
import com.example.myarchitecture.shared.data.networking.ExceptionHandler
import com.example.myarchitecture.shared.data.networking.NetworkState
import com.example.myarchitecture.shared.utils.NetworkStatusUtils
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

open class BaseService {

    @Inject
    lateinit var exceptionHandler: ExceptionHandler

    init {
        App.instance.getPersonComponent().inject(this)
    }

    suspend fun <T> callAsync(method: suspend () -> Response<ResponseModel<T>>): T? {
        try {
            val response = method()

            if (!response.isSuccessful) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    exceptionHandler.onError(NetworkState.UN_AUTHORIZATION_ERROR)
                else
                    exceptionHandler.onError(NetworkState.SERVER_ERROR)
                return null
            }
            if (!response.body()?.success!!) {
                var message: String? = null
                val messages = response.body()?.messages
                if (messages != null && messages.isNotEmpty())
                    message = messages[0].value
                exceptionHandler.onError(NetworkState(NetworkState.Status.API_ERROR, message))
                return null
            }
            return response.body()!!.data
        } catch (e: Throwable) {
            if (NetworkStatusUtils.isNetworkAvailable())
                exceptionHandler.onError(NetworkState.SERVER_ERROR)
            else
                exceptionHandler.onError(NetworkState.NETWORK_ERROR)
            return null
        }
    }
}