package com.example.myarchitecture.shared.data.api

import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IPersonService {
    @POST("/api/User/NotificationList")
    suspend fun getNotificationsList(@Body responseModel: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<NotificationModel>>>>
}