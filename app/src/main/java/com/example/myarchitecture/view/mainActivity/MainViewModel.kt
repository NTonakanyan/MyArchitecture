package com.example.myarchitecture.view.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.shared.dataSource.PagingDataSource
import com.example.myarchitecture.view.baseView.BaseViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext


class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private var notificationLiveData: LiveData<PagedList<NotificationModel>>? = null
    private var liveData = MutableLiveData<List<NotificationModel>>()

    /**
     * Paging request example
     */

    init {
        App.instance.getPersonComponent().inject(this)

        val notificationDataDataSourceCreator = object : DataSource.Factory<Int, NotificationModel>() {
            override fun create(): DataSource<Int, NotificationModel> {
                val model = PaginationRequestModel()
                model.page = 1
                model.count = 5
                val function: suspend (Boolean) -> PaginationResponseModel<List<NotificationModel>>? = { mService.getNotificationsList(model, it) }
                return PagingDataSource(scope, mExceptionHandler, function)
            }
        }
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(5)
            .build()
        notificationLiveData = LivePagedListBuilder<Int, NotificationModel>(notificationDataDataSourceCreator, config).build()

        val repeatFun: (String, Int) -> String = { it1, it2 -> it1.repeat(it2) }
        runTransformation(repeatFun) // OK
    }

    private fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }

     fun getNotificationLiveData(): LiveData<PagedList<NotificationModel>>? {
        return notificationLiveData
    }

    /**
     * Normal request example
     */

    fun getLiveData(): LiveData<List<NotificationModel>>? {
        return liveData
    }

    fun api() {
        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            val model = PaginationRequestModel()
            model.page = 1
            model.count = 5
            withContext(Dispatchers.Main) {
                val responseModel = mService.getNotificationsList(model, true)
                if (responseModel?.data != null)
                    liveData.postValue(responseModel.data)
            }
        }
    }
}