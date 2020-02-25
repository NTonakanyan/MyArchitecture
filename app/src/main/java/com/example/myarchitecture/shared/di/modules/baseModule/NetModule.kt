package com.example.myarchitecture.shared.di.modules.baseModule

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.myarchitecture.BuildConfig
import com.example.myarchitecture.shared.data.networking.NetworkAvailable
import com.example.myarchitecture.shared.data.networking.RequestState
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import com.example.myarchitecture.shared.utils.CommonUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    private val mMaxStale = 60 * 60 * 24 * 5 // 5-days
    private var language: Int = 0

    @Provides
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, application: Application, shared: SharedPreferencesHelper): OkHttpClient {
        return OkHttpClient.Builder()
//            .addInterceptor(ChuckInterceptor(application))
            .addInterceptor(CustomInterceptor(shared, application))
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideMutableLiveData(): MutableLiveData<RequestState> {
        return MutableLiveData()
    }

    inner class CustomInterceptor internal constructor(private val mShared: SharedPreferencesHelper,
                                                       private val mApplication: Application) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            val token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJuYXJla25hcmVrNDEwMDFAZ21haWwuY29tIiwianRpIjoiMWM0Yjg2NjgtMDcxZi00MzJhLThhZmYtYzFlNTVhOTJiZTk2IiwiaWF0IjpbMjUsMjVdLCJ1c2VySWQiOiIxIiwicm9sZXMiOiJVc2VyIiwiRXhwaXJlRGF0ZSI6IjAyLzI5LzIwMjAgMTE6NTQ6MjUiLCJ2ZXJpZmllZEJ5IjoiRW1haWwiLCJuYmYiOjE1ODAzODUyNjUsImV4cCI6MTU4Mjk3NzI2NSwiaXNzIjoiQmFpdGttUHJvZHVjdGlvbiIsImF1ZCI6IkJhaXRrbVJlYWN0SnNXZWJBcHAifQ.Ww0IYEdeReIYK59BwnIz0WtXsg2ymrjtbe1sFwy83oY"

//            val token = "Bearer ${mShared.getStringSharedPreferences(AppConstants.TOKEN)}"
            val cacheControl = if (NetworkAvailable.isNetworkAvailable())
                "public, max-age=$mMaxStale"
            else
                "public, max-stale=$mMaxStale"
            val deviceId = CommonUtils.getDeviceId(mApplication)
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .header("Cache-Control", cacheControl)
                .header("DeviceId", deviceId)
                .header("OsType", "0")
                .header("Language", language.toString())
                .build()

            val response = chain.proceed(request)
            response.code()
            return response
        }
    }
}