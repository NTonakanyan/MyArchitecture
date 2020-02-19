package com.example.myarchitecture.shared.data.services.baseService

import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.shared.data.networking.RequestHandler
import com.example.myarchitecture.shared.data.networking.NetworkAvailable
import com.example.myarchitecture.shared.data.networking.RequestState
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

open class BaseService {

    @Inject
    lateinit var mRequestHandler: RequestHandler

    init {
        App.instance.getPersonComponent().inject(this)
    }

    suspend fun <T> callAsync(isMainRequest: Boolean, method: suspend () -> Response<ResponseModel<T>>): T? {
        try {
            mRequestHandler.onError(RequestState(isMainRequest, RequestState.Status.LOADING, null))
            val response = method()

            if (!response.isSuccessful) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    App.instance.unAuthorization()
                else
                    mRequestHandler.onError(RequestState.createRequestState(RequestState.Status.SERVER_ERROR, isMainRequest))
                return null
            }
            if (!response.body()?.success!!) {
                var message: String? = null
                val messages = response.body()?.messages
                if (messages != null && messages.isNotEmpty())
                    message = messages[0].value
                mRequestHandler.onError(RequestState(isMainRequest, RequestState.Status.API_ERROR, message))
                return null
            }
            mRequestHandler.onError(RequestState.createRequestState(RequestState.Status.SUCCESS, isMainRequest))
            return response.body()!!.data
        } catch (e: Throwable) {
            if (NetworkAvailable.isNetworkAvailable())
                mRequestHandler.onError(RequestState.createRequestState(RequestState.Status.SERVER_ERROR, isMainRequest))
            else
                mRequestHandler.onError(RequestState.createRequestState(RequestState.Status.NETWORK_ERROR, isMainRequest))
            return null
        }
    }
}