package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.AccountFragment
import com.codemitry.wearer.di.modules.AccountModule
import com.codemitry.wearer.di.modules.GetUserUseCaseModule
import com.codemitry.wearer.di.modules.SignOutUseCaseModule
import com.codemitry.wearer.mvp.contracts.account.AccountContract
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Component(modules = [AccountModule::class, SignOutUseCaseModule::class, GetUserUseCaseModule::class])
@Singleton
interface AccountComponent {
    fun inject(fragment: AccountFragment)

    fun presenter(): AccountContract.Presenter

    }