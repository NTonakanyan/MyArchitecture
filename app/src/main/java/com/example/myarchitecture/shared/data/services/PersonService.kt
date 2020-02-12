package com.example.myarchitecture.shared.data.services

import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.api.IPersonService
import com.example.myarchitecture.shared.data.services.baseService.BaseService

class PersonService(private val mService: IPersonService) : BaseService() {

    suspend fun getNotificationsList(model: PaginationRequestModel): PaginationResponseModel<List<NotificationModel>>? {
        return callAsync { mService.getNotificationsList(model) }
    }
}