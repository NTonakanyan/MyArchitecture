package com.example.myarchitecture

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import com.example.myarchitecture.shared.di.components.IPersonComponent
import com.example.myarchitecture.shared.di.components.root.DaggerIAppComponent
import com.example.myarchitecture.shared.di.components.root.IAppComponent
import com.example.myarchitecture.shared.di.modules.PersonModule
import com.example.myarchitecture.shared.di.modules.root.AppModule
import com.example.myarchitecture.shared.di.modules.root.NetModule
import com.example.myarchitecture.shared.helpers.SharedPreferencesHelper
import com.example.myarchitecture.view.mainActivity.MainActivity

class App : Application() {

    private lateinit var mAppComponent: IAppComponent
    companion object {
        lateinit var instance: App
    }

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()
        instance = this

        mAppComponent = DaggerIAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }

    fun getPersonComponent(): IPersonComponent {
        return mAppComponent.iPersonComponent(PersonModule())
    }

    fun unAuthorization() {
        SharedPreferencesHelper(this).deleteSharedPreferences()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }
}