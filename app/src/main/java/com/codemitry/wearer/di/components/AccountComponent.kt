package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.AccountFragment
import com.codemitry.wearer.di.modules.AccountModule
import com.codemitry.wearer.di.modules.SignOutUseCaseModule
import com.codemitry.wearer.mvp.contracts.account.AccountContract
import dagger.Component

@Component(modules = [AccountModule::class, SignOutUseCaseModule::class])

interface AccountComponent {
    fun inject(fragment: AccountFragment)

    fun presenter(): AccountContract.Presenter

    }