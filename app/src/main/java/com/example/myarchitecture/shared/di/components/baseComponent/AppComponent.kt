package com.example.myarchitecture.shared.di.components.baseComponent

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.di.components.AuthorizationComponent
import com.example.myarchitecture.shared.di.components.PersonComponent
import com.example.myarchitecture.shared.di.modules.AuthorizationModule
import com.example.myarchitecture.shared.di.modules.PersonModule
import com.example.myarchitecture.shared.di.modules.baseModule.AppModule
import com.example.myarchitecture.shared.di.modules.baseModule.NetModule
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun myBackendRetrofit(): Retrofit

    fun context(): Context

    fun resources(): Resources

    fun sharedPreferences(): SharedPreferencesHelper

    fun iAuthorizationComponent(module: AuthorizationModule): AuthorizationComponent

    fun iPersonComponent(module: PersonModule): PersonComponent

    fun requestStateLiveData(): MutableLiveData<RequestState>
}