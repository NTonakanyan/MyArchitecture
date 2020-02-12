package com.example.myarchitecture.shared.data.services

import com.example.myarchitecture.model.PaginationRequestModel
import com.example.myarchitecture.model.PaginationResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.api.IPersonService
import com.example.myarchitecture.shared.data.services.root.BaseService
import retrofit2.Response
import java.util.*

class PersonService(private val mService: IPersonService) : BaseService() {

    suspend fun getNotificationsList(model: PaginationRequestModel): PaginationResponseModel<List<NotificationModel>>? {
        return callAsync { mService.getNotificationsList(model) }
    }
}