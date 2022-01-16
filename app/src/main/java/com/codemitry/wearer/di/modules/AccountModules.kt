package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.account.AccountContract
import com.codemitry.wearer.mvp.presenters.AccountPresenter
import com.codemitry.wearer.mvp.usecases.GetUserUseCase
import com.codemitry.wearer.mvp.usecases.SignOutUseCase
import com.codemitry.wearer.mvp.usecases.SignOutUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [GetUserUseCaseModule::class])
class AccountModule {

    @Provides
    fun provideAccountPresenter(signOutUseCase: SignOutUseCase, getUserUseCase: GetUserUseCase): AccountContract.Presenter {
        return AccountPresenter(signOutUseCase,getUserUseCase)
    }
}

@Module
class SignOutUseCaseModule {

    @Provides
    @Singleton
    fun provideSignOutUseCase(): SignOutUseCase {
        return SignOutUseCaseImpl()
    }
}