package com.example.myarchitecture.shared.data.api

import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IPersonService {
    @POST("/api/User/NotificationList")
    suspend fun getNotificationsList(@Body model: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<NotificationModel>>>>

    @POST("/api/Announcement/Suggesting")
    suspend fun getSuggestedAnnouncement(@Body model: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<AnnouncementModel>>>>
}