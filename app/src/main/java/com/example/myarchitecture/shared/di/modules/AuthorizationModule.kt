package com.example.myarchitecture.shared.di.modules

import com.example.myarchitecture.shared.data.api.IAuthorizationService
import com.example.myarchitecture.shared.di.scopes.AuthorizationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthorizationModule {

    @Provides
    @AuthorizationScope
    internal fun providesIAuthorizationService(retrofit: Retrofit): IAuthorizationService {
        return retrofit.create(IAuthorizationService::class.java)
    }
}