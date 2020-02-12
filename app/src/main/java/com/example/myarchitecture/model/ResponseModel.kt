package com.example.myarchitecture.model

class ResponseModel<T> {
    val success: Boolean? = false
    val messages: List<MessageModel>? = null
    val data: T? = null
}