package com.example.myarchitecture.shared.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myarchitecture.model.PaginationRequestModel
import com.example.myarchitecture.model.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class PagingDataSource<T>(private val scope: CoroutineScope,
                          private val pagingStateLiveData: MutableLiveData<NetworkState>,
                          private val method: suspend () -> PaginationResponseModel<List<T>>?) : PageKeyedDataSource<Int, T>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        pagingStateLiveData.postValue(NetworkState.LOADING)
        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            val response = method()
            if (response?.data != null) {
                callback.onResult(response.data, null, 2)
                pagingStateLiveData.postValue(NetworkState.LOADED)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        pagingStateLiveData.postValue(NetworkState.LOADING)
        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            val model = PaginationRequestModel()
            model.page = params.key
            model.count = params.requestedLoadSize
            val response = method()
            if (response?.data != null) {
                val nextKey = if (params.key == response.pageCount) null else params.key + 1
                callback.onResult(response.data, nextKey)
                pagingStateLiveData.postValue(NetworkState.LOADED)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }
}