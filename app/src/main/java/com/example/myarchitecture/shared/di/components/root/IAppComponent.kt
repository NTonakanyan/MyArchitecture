package com.example.myarchitecture.shared.di.components.root

import android.content.Context
import android.content.res.Resources
import com.example.myarchitecture.shared.data.networking.ExceptionHandler
import com.example.myarchitecture.shared.di.components.IAuthorizationComponent
import com.example.myarchitecture.shared.di.components.IPersonComponent
import com.example.myarchitecture.shared.di.modules.AuthorizationModule
import com.example.myarchitecture.shared.di.modules.PersonModule
import com.example.myarchitecture.shared.di.modules.root.AppModule
import com.example.myarchitecture.shared.di.modules.root.NetModule
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface IAppComponent {
    fun myBackendRetrofit(): Retrofit

    fun context(): Context

    fun resources(): Resources

    fun sharedPreferences(): SharedPreferencesHelper

    fun iAuthorizationComponent(module: AuthorizationModule): IAuthorizationComponent

    fun iPersonComponent(module: PersonModule): IPersonComponent

    fun exceptionHandler(): ExceptionHandler
}