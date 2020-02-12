package com.example.myarchitecture.shared.di.modules.root

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    internal fun providesSharedPreferences(application: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }
}