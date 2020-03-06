package com.example.myarchitecture.shared.data.services

import androidx.lifecycle.MutableLiveData
import com.example.myarchitecture.model.announcementModels.AnnouncementDetailsModel
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.api.IPersonService
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.data.services.baseService.BaseService

class PersonService(private val mService: IPersonService) : BaseService() {

    suspend fun getNotificationsList(model: PaginationRequestModel, isMainRequest:Boolean, requestHandler: MutableLiveData<RequestState>): PaginationResponseModel<List<NotificationModel>>? {
        return callAsync(requestHandler,isMainRequest) { mService.getNotificationsList(model) }
    }

    suspend fun getSuggestedAnnouncementList(model: PaginationRequestModel, isMainRequest:Boolean,requestHandler: MutableLiveData<RequestState>): PaginationResponseModel<List<AnnouncementModel>>? {
        return callAsync(requestHandler,isMainRequest) { mService.getSuggestedAnnouncement(model) }
    }

    suspend fun getAnnouncementDetails(id: Int?, isMainRequest:Boolean,requestHandler: MutableLiveData<RequestState>): AnnouncementDetailsModel? {
        return callAsync(requestHandler,isMainRequest) { mService.getAnnouncementDetails(id) }
    }
}