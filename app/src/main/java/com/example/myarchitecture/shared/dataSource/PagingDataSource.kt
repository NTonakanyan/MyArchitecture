package com.example.myarchitecture.shared.dataSource

import androidx.paging.PageKeyedDataSource
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.ExceptionHandler
import com.example.myarchitecture.shared.data.networking.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class PagingDataSource<T>(private val scope: CoroutineScope,
                          private val requestStateLiveDate: ExceptionHandler,
                          private val method: suspend (b: Boolean) -> PaginationResponseModel<List<T>>?) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            requestStateLiveDate.onError(RequestState(true, RequestState.Status.LOADING, null))
            val response = method(true)
            if (response?.data != null) {
                if (response.data.isEmpty())
                    requestStateLiveDate.onError(RequestState(true, RequestState.Status.EMPTY, null))
                callback.run { onResult(response.data, null, 2) }
                requestStateLiveDate.onError(RequestState(true, RequestState.Status.SUCCESS, null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            requestStateLiveDate.onError(RequestState(false, RequestState.Status.LOADING, null))
            val response = method(false)
            if (response?.data != null) {
                val nextKey = if (params.key == response.pageCount) null else params.key + 1
                callback.onResult(response.data, nextKey)
                requestStateLiveDate.onError(RequestState(false, RequestState.Status.SUCCESS, null))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }
}