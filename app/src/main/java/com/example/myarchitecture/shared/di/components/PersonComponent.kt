package com.example.myarchitecture.shared.di.components

import com.example.myarchitecture.shared.data.services.baseService.BaseService
import com.example.myarchitecture.shared.di.modules.PersonModule
import com.example.myarchitecture.shared.di.scopes.PersonScope
import com.example.myarchitecture.view.baseView.BaseViewModel
import com.example.myarchitecture.view.mainActivity.MainViewModel
import dagger.Subcomponent

@PersonScope
@Subcomponent(modules = [PersonModule::class])
interface PersonComponent {
    fun inject(viewModel: BaseViewModel)

    fun inject(baseService: BaseService)

    fun inject(viewModel: MainViewModel)
}