package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.AccountComponent
import com.codemitry.wearer.di.components.DaggerAccountComponent
import com.codemitry.wearer.di.delegates.AccountComponentBuilder
import com.codemitry.wearer.di.modules.AccountModule
import com.codemitry.wearer.di.modules.SignOutUseCaseModule

class AccountComponentBuilderImpl : AccountComponentBuilder {
    override fun build(): AccountComponent =
        DaggerAccountComponent.builder()
            .signOutUseCaseModule(SignOutUseCaseModule())
            .accountModule(AccountModule())
            .build()
}