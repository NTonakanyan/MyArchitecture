package com.example.myarchitecture.shared.data.api

import com.example.myarchitecture.model.announcementModels.AnnouncementDetailsModel
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.model.baseModels.PaginationRequestModel
import com.example.myarchitecture.model.baseModels.PaginationResponseModel
import com.example.myarchitecture.model.baseModels.ResponseModel
import com.example.myarchitecture.model.notificationModels.NotificationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPersonService {
    @POST("/api/User/NotificationList")
    suspend fun getNotificationsList(@Body model: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<NotificationModel>>>>

    @POST("/api/Announcement/Suggesting")
    suspend fun getSuggestedAnnouncement(@Body model: PaginationRequestModel): Response<ResponseModel<PaginationResponseModel<List<AnnouncementModel>>>>

    @GET("/api/Announcement/MyAnnouncementDetails/{announcementId}")
    suspend fun getAnnouncementDetails(@Path("announcementId") id: Int?): Response<ResponseModel<AnnouncementDetailsModel>>
}