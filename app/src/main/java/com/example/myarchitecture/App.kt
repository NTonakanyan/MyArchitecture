package com.example.myarchitecture

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import com.armboldmind.exceptionlibrary.ExceptionHandler
import com.example.myarchitecture.shared.di.components.PersonComponent
import com.example.myarchitecture.shared.di.components.baseComponent.AppComponent
import com.example.myarchitecture.shared.di.components.baseComponent.DaggerAppComponent
import com.example.myarchitecture.shared.di.modules.PersonModule
import com.example.myarchitecture.shared.di.modules.baseModule.AppModule
import com.example.myarchitecture.shared.di.modules.baseModule.NetModule
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import com.example.myarchitecture.view.mainActivity.MainActivity

class App : Application() {

    private lateinit var mAppComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        ExceptionHandler.init(this)
        instance = this

        mAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }

    fun getPersonComponent(): PersonComponent {
        return mAppComponent.iPersonComponent(PersonModule())
    }

    fun unAuthorization() {
        SharedPreferencesHelper(this).deleteSharedPreferences()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }
}