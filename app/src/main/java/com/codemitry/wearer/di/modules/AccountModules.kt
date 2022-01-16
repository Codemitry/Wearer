package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.account.AccountContract
import com.codemitry.wearer.mvp.presenters.AccountPresenter
import com.codemitry.wearer.mvp.usecases.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [GetUserUseCaseModule::class, DeleteAccountUseCaseModule::class])
class AccountModule {

    @Provides
    fun provideAccountPresenter(signOutUseCase: SignOutUseCase, getUserUseCase: GetUserUseCase, deleteAccountUseCase: DeleteAccountUseCase): AccountContract.Presenter {
        return AccountPresenter(signOutUseCase,getUserUseCase, deleteAccountUseCase)
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

@Module
class DeleteAccountUseCaseModule {

    @Provides
    @Singleton
    fun provideDeleteAccountUseCase(): DeleteAccountUseCase {
        return DeleteAccountUseCaseImpl()
    }
}