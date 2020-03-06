package com.example.myarchitecture.view.mainActivity.fragments.homeFragment

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.view.baseView.BaseViewModel
import javax.inject.Inject

@PersonScope
class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private var notificationLiveData = MediatorLiveData<PagedList<NotificationModel>>()

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getNotifications(isMainRequest: Boolean = true) {
        val function: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<NotificationModel>>? = { it1, it2 ->
            mService.getNotificationsList(it1, if (isMainRequest) it2 else isMainRequest, mRequestLiveData)
        }
        val ld = initPaginationDataSours(function)
        notificationLiveData.addSource(ld) {
            notificationLiveData.postValue(it)
            notificationLiveData.removeSource(ld)
        }
    }

    fun getNotificationLiveData(): MediatorLiveData<PagedList<NotificationModel>> {
        return notificationLiveData
    }
}