package com.example.myarchitecture.view.mainActivity.fragments.peopleFragment

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.myarchitecture.App
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.view.baseView.BaseViewModel
import javax.inject.Inject

class AnnouncementViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private var announcementsLiveData: LiveData<PagedList<AnnouncementModel>>? = null

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getAnnouncementsLiveData(): LiveData<PagedList<AnnouncementModel>>? {
        return announcementsLiveData
    }

    fun getAnnouncements() {
        val function: suspend (PaginationRequestModel, Boolean) -> PaginationResponseModel<List<AnnouncementModel>>? = { it1, it2 -> mService.getSuggestedAnnouncementList(it1, it2) }
        announcementsLiveData = initPaginationDataSours(function)
    }

//    fun api() {
//        scope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
//            val model = PaginationRequestModel()
//            model.page = 1
//            model.count = 5
//            withContext(Dispatchers.Main) {
//                val responseModel = mService.getNotificationsList(model, true)
//                if (responseModel?.data != null)
//                    liveData.postValue(responseModel.data)
//            }
//        }
//    }
}