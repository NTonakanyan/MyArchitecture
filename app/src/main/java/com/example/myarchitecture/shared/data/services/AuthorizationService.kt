package com.example.myarchitecture.shared.data.services

import com.example.myarchitecture.shared.data.api.IAuthorizationService
import com.example.myarchitecture.shared.data.services.root.BaseService

class AuthorizationService(private val mService: IAuthorizationService) : BaseService() {
//    suspend fun get(phone: String, callBack: ResponseCallBack<String>) {
//        callAsync(method = { mService.verificationCodeAsync(phone).await() }, callBack = callBack)
//    }
}