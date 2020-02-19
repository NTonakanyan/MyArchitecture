package com.example.myarchitecture.shared.data.services

import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.api.IPersonService
import com.example.myarchitecture.shared.data.services.baseService.BaseService

class PersonService(private val mService: IPersonService) : BaseService() {

    suspend fun getNotificationsList(model: PaginationRequestModel, isMainRequest:Boolean): PaginationResponseModel<List<NotificationModel>>? {
        return callAsync(isMainRequest) { mService.getNotificationsList(model) }
    }

    suspend fun getSuggestedAnnouncementList(model: PaginationRequestModel, isMainRequest:Boolean): PaginationResponseModel<List<AnnouncementModel>>? {
        return callAsync(isMainRequest) { mService.getSuggestedAnnouncement(model) }
    }
}