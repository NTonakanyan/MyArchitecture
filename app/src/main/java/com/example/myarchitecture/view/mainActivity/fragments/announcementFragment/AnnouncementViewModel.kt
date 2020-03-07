package com.example.myarchitecture.view.mainActivity.fragments.announcementFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.view.baseView.BaseViewModel
import javax.inject.Inject

@PersonScope
class AnnouncementViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private var announcementsLiveData = MediatorLiveData<PagedList<AnnouncementModel>>()

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getAnnouncements(isMainRequest: Boolean = true) {
        val function: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<AnnouncementModel>>? = { it1, it2 ->
            mService.getSuggestedAnnouncementList(it1, if (isMainRequest) it2 else isMainRequest, mRequestLiveData)
        }
        val ld = initPaginationDataSours(function,2)
        announcementsLiveData.addSource(ld) {
            announcementsLiveData.postValue(it)
            announcementsLiveData.removeSource(ld)
        }
    }

    fun getAnnouncementsLiveData(): LiveData<PagedList<AnnouncementModel>> {
        return announcementsLiveData
    }
}