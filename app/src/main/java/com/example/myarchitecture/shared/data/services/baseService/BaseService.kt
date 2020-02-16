package com.example.myarchitecture.shared.data.services.baseService

import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.shared.data.networking.ExceptionHandler
import com.example.myarchitecture.shared.data.networking.NetworkAvailable
import com.example.myarchitecture.shared.data.networking.RequestState
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

open class BaseService {

    @Inject
    lateinit var exceptionHandler: ExceptionHandler

    init {
        App.instance.getPersonComponent().inject(this)
    }

    suspend fun <T> callAsync(isMainRequest: Boolean, method: suspend () -> Response<ResponseModel<T>>): T? {
        try {
            exceptionHandler.onError(RequestState(isMainRequest, RequestState.Status.LOADING, null))
            val response = method()

            if (!response.isSuccessful) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                exceptionHandler.onError(RequestState.createRequestState(RequestState.Status.UN_AUTHORIZATION_ERROR,isMainRequest))
                else
                exceptionHandler.onError(RequestState.createRequestState(RequestState.Status.SERVER_ERROR,isMainRequest))
                return null
            }
            if (!response.body()?.success!!) {
                var message: String? = null
                val messages = response.body()?.messages
                if (messages != null && messages.isNotEmpty())
                    message = messages[0].value
                exceptionHandler.onError(RequestState(isMainRequest, RequestState.Status.API_ERROR, message))
                return null
            }
            exceptionHandler.onError(RequestState.createRequestState(RequestState.Status.SUCCESS,isMainRequest))
            return response.body()!!.data
        } catch (e: Throwable) {
            if (NetworkAvailable.isNetworkAvailable())
                exceptionHandler.onError(RequestState.createRequestState(RequestState.Status.SERVER_ERROR,isMainRequest))
            else
                exceptionHandler.onError(RequestState.createRequestState(RequestState.Status.NETWORK_ERROR,isMainRequest))
            return null
        }
    }
}