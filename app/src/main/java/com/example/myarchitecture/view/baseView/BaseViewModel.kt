package com.example.myarchitecture.view.baseView

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myarchitecture.App
import com.example.myarchitecture.shared.data.networking.ExceptionHandler
import com.example.myarchitecture.shared.data.networking.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var exceptionHandler: ExceptionHandler
    @Inject
    lateinit var context: Context

    init {
        App.instance.getPersonComponent().inject(this)

        exceptionHandler.setOnExceptionHandler(object : ExceptionHandler.IExceptionHandler {
            override fun onError(networkState: NetworkState?) {
                mErrorLiveData.postValue(networkState)
            }
        })
    }

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)
    fun cancelAllRequests() = coroutineContext.cancel()


    val mErrorLiveData = MutableLiveData<NetworkState>()


//    protected fun errorToast(errorMessage: String) {
//        mToastMessageLiveData.postValue(errorMessage)
//    }
//
//    protected fun errorSnackBar(errorMessage: String) {
//        mSnackBarMessageLiveData.postValue(errorMessage)
//    }

//    fun showFailure(message: Any) {
//        if (message is String) {
//            mSnackBarMessageLiveData.postValue(message)
//        } else {
//            mServerErrorLiveData.postValue(true)
//        }
//    }

//    protected fun errorView(networkError: NetworkError) {
//        if (NetworkStatusUtils.isNetworkAvailable())
//            mNetworkErrorLiveData.setValue(true)
//        else
//            mServerErrorLiveData.setValue(true)
//    }
}