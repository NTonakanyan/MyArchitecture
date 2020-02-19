package com.example.myarchitecture.shared.dataSource

import androidx.paging.PageKeyedDataSource
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.RequestHandler
import com.example.myarchitecture.shared.data.networking.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class PagingDataSource<T>(private val mScope: CoroutineScope,
                          private val mRequestHandler: RequestHandler,
                          private val mMethod: suspend (model: PaginationRequestModel, isMainRequest: Boolean) -> PaginationResponseModel<List<T>>?) : PageKeyedDataSource<Int, T>() {

    companion object {
        const val FIRST_PAGE = 4
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        mScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            mRequestHandler.onError(RequestState(true, RequestState.Status.LOADING, null))
            val model = PaginationRequestModel()
            model.page = 1
            model.count = params.requestedLoadSize
            val response = mMethod(model, true)
            if (response?.data != null) {
                if (response.data.isEmpty())
                    mRequestHandler.onError(RequestState(true, RequestState.Status.EMPTY, null))
                callback.run { onResult(response.data, null, FIRST_PAGE) }
                mRequestHandler.onError(RequestState(true, RequestState.Status.SUCCESS, null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        mScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            mRequestHandler.onError(RequestState(false, RequestState.Status.LOADING, null))
            val model = PaginationRequestModel()
            model.page = params.key
            model.count = params.requestedLoadSize
            val response = mMethod(model, false)
            if (response?.data != null) {
                val nextKey = if (params.key == response.pageCount) null else params.key + 1
                callback.onResult(response.data, nextKey)
                mRequestHandler.onError(RequestState(false, RequestState.Status.SUCCESS, null))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }
}