package com.example.myarchitecture.view.mainActivity.fragments.announcementDetailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myarchitecture.App
import com.example.myarchitecture.model.announcementModels.AnnouncementDetailsModel
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.view.baseView.BaseViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@PersonScope
class AnnouncementDetailsViewModel : BaseViewModel() {

    @Inject
    lateinit var mService: PersonService
    private val announcementLiveData = MutableLiveData<AnnouncementDetailsModel>()

    init {
        App.instance.getPersonComponent().inject(this)
    }

    fun getAnnouncementDetails(id: Int?) {
        viewModelScope.launch(EmptyCoroutineContext, CoroutineStart.DEFAULT) {
            announcementLiveData.postValue(mService.getAnnouncementDetails(id, true, mRequestLiveData))
        }
    }

    fun getAnnouncementDetailsLiveData(): LiveData<AnnouncementDetailsModel> {
        return announcementLiveData
    }
}