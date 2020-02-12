package com.example.myarchitecture.shared.data.api

import com.example.myarchitecture.model.PaginationRequestModel
import com.example.myarchitecture.model.PaginationResponseModel
import com.example.myarchitecture.model.ResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface IPersonService {
    @POST("/api/User/NotificationList")
    suspend fun getNotificationsList(@Body responseModel: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<NotificationModel>>>>
}