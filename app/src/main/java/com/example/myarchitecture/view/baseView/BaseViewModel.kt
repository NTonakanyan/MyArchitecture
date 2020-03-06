package com.example.myarchitecture.view.baseView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.dataSource.PagingDataSource
import kotlinx.coroutines.cancelChildren

open class BaseViewModel : ViewModel() {

    protected val mRequestLiveData = MutableLiveData<RequestState>()

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getLiveData(): MutableLiveData<RequestState> {
        return mRequestLiveData
    }

    protected fun <T> initPaginationDataSours(method: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<T>>?, loadedItemCount: Int = 5): LiveData<PagedList<T>> {
        val notificationDataDataSourceCreator = object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T> {
                return PagingDataSource(viewModelScope, mRequestLiveData,  method)
            }
        }
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(loadedItemCount)
            .build()
        return LivePagedListBuilder(notificationDataDataSourceCreator, config).build()
    }

    fun closeRequest() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}