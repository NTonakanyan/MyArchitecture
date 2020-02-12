package com.example.myarchitecture.shared.di.components

import com.example.myarchitecture.shared.di.modules.AuthorizationModule
import com.example.myarchitecture.shared.di.scopes.AuthorizationScope
import dagger.Subcomponent

@AuthorizationScope
@Subcomponent(modules = [AuthorizationModule::class])
interface IAuthorizationComponent {
}