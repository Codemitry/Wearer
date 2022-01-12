package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.account.AccountContract
import com.codemitry.wearer.mvp.presenters.AccountPresenter
import com.codemitry.wearer.mvp.usecases.SignOutUseCase
import com.codemitry.wearer.mvp.usecases.SignOutUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class AccountModule {

    @Provides
    fun provideAccountPresenter(signOutUseCase: SignOutUseCase): AccountContract.Presenter {
        return AccountPresenter(signOutUseCase)
    }
}

@Module
class SignOutUseCaseModule {

    @Provides
    fun provideSignOutUseCase(): SignOutUseCase {
        return SignOutUseCaseImpl()
    }
}