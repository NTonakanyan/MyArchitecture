package com.example.myarchitecture.view.baseView

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.networking.RequestHandler
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.dataSource.PagingDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var mRequestHandler: RequestHandler
    @Inject
    lateinit var context: Context
    val mErrorLiveData = MutableLiveData<RequestState>()

    init {
        App.instance.getPersonComponent().inject(this)

        mRequestHandler.setOnExceptionHandler(object : RequestHandler.IExceptionHandler {
            override fun onChanged(requestState: RequestState?) {
                mErrorLiveData.postValue(requestState)
            }
        })
    }

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    protected val scope = CoroutineScope(coroutineContext)

    protected fun <T> initPaginationDataSours(method: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<T>>?, loadedItemCount: Int = 5): LiveData<PagedList<T>>? {
        val notificationDataDataSourceCreator = object : DataSource.Factory<Int, T>() {
            override fun create(): DataSource<Int, T> {
                return PagingDataSource(scope, mRequestHandler, method)
            }
        }
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(loadedItemCount)
            .build()
        return LivePagedListBuilder<Int, T>(notificationDataDataSourceCreator, config).build()
    }
}