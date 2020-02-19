package com.example.myarchitecture.view.mainActivity.fragments.homeFragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.view.baseView.BaseViewModel
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private var notificationLiveData: LiveData<PagedList<NotificationModel>>? = null

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getNotifications() {
        val function: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<NotificationModel>>? = { it1, it2 -> mService.getNotificationsList(it1, it2) }
        notificationLiveData = initPaginationDataSours(function)
    }

    fun getNotificationLiveData(): LiveData<PagedList<NotificationModel>>? {
        return notificationLiveData
    }
}