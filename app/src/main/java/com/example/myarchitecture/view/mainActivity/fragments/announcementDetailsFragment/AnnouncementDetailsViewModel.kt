package com.example.myarchitecture.view.mainActivity.fragments.announcementDetailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myarchitecture.App
import com.example.myarchitecture.model.announcementModels.AnnouncementDetailsModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.view.baseView.BaseViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

class AnnouncementDetailsViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private val announcementLiveData = MutableLiveData<AnnouncementDetailsModel>()

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getAnnouncementDetails(id: Int?) {
        mScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            announcementLiveData.postValue(mService.getAnnouncementDetails(id, true))
        }
    }

    fun getAnnouncementDetailsLiveData(): LiveData<AnnouncementDetailsModel> {
        return announcementLiveData
    }
}