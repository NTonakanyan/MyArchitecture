package com.example.myarchitecture.shared.di.modules

import com.example.myarchitecture.shared.data.api.IPersonService
import com.example.myarchitecture.shared.data.services.PersonService
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.shared.utils.AppConstants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class PersonModule {

    @Provides
    @PersonScope
    fun provideIPersonService(retrofit: Retrofit): IPersonService {
        return retrofit.create(IPersonService::class.java)
    }

    @Provides
    @PersonScope
    fun providesPersonService(service: IPersonService): PersonService {
        return PersonService(service)
    }
}