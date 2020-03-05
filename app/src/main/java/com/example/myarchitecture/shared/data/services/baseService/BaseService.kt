package com.example.myarchitecture.shared.data.services.baseService

import androidx.lifecycle.MutableLiveData
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.shared.data.networking.NetworkAvailable
import com.example.myarchitecture.shared.data.networking.RequestState
import kotlinx.coroutines.isActive
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

open class BaseService {

    @Inject
    lateinit var mRequestHandler: MutableLiveData<RequestState>

    init {
        App.instance.getPersonComponent().inject(this)
    }

    suspend fun <T> callAsync(isMainRequest: Boolean, method: suspend () -> Response<ResponseModel<T>>): T? {
        try {
            mRequestHandler.postValue(RequestState(isMainRequest, RequestState.Status.LOADING, null))
            val response = method()

            if (!response.isSuccessful) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || response.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    App.instance.unAuthorization()
                else
                    mRequestHandler.postValue(RequestState.createRequestState(RequestState.Status.SERVER_ERROR, isMainRequest))
                return null
            }
            if (!response.body()?.success!!) {
                var message: String? = null
                val messages = response.body()?.messages
                if (messages != null && messages.isNotEmpty())
                    message = messages[0].value
                mRequestHandler.postValue(RequestState(isMainRequest, RequestState.Status.API_ERROR, message))
                return null
            }
            mRequestHandler.postValue(RequestState.createRequestState(RequestState.Status.SUCCESS, isMainRequest))
            return response.body()!!.data
        } catch (e: Throwable) {
            if (coroutineContext.isActive)
                if (NetworkAvailable.isNetworkAvailable())
                    mRequestHandler.postValue(RequestState.createRequestState(RequestState.Status.SERVER_ERROR, isMainRequest))
                else
                    mRequestHandler.postValue(RequestState.createRequestState(RequestState.Status.NETWORK_ERROR, isMainRequest))
            return null
        }
    }
}