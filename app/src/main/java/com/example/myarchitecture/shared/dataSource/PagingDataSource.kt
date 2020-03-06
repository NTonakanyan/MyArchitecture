package com.example.myarchitecture.shared.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class PagingDataSource<T>(private val mScope: CoroutineScope,
                          private val mRequestHandler: MutableLiveData<RequestState>,
                          private val mMethod: suspend (model: PaginationRequestModel, isMainRequest: Boolean) -> PaginationResponseModel<List<T>>?) : PageKeyedDataSource<Int, T>() {

    companion object {
        const val NEXT_PAGE = 4 // because requested load size multiply 3
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        mScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            val model = PaginationRequestModel()
            model.page = 1
            model.count = params.requestedLoadSize
            val response = mMethod(model, true)
            if (response?.data != null) {
                val nextKey = if (model.page == response.pageCount) null else NEXT_PAGE
                callback.onResult(response.data,null, nextKey)
                if (response.data.isEmpty())
                    mRequestHandler.postValue(RequestState(true, RequestState.Status.EMPTY, null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        mScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            val model = PaginationRequestModel()
            model.page = params.key
            model.count = params.requestedLoadSize
            val response = mMethod(model, false)
            if (response?.data != null) {
                val nextKey = if (params.key == response.pageCount) null else params.key + 1
                callback.onResult(response.data, nextKey)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }
}