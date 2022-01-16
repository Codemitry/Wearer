package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.codemitry.wearer.mvp.presenters.SignInInteractor
import com.codemitry.wearer.mvp.presenters.SignInPresenter
import com.codemitry.wearer.mvp.usecases.GetUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [GetUserUseCaseModule::class])
open class SignInInteractorModule {

    @Singleton
    @Provides
    open fun provideSignInInteractor(getUserUseCase: GetUserUseCase): SignInContract.SignInInteractor {
        return SignInInteractor(getUserUseCase)
    }
}

@Module
open class SignInPresenterModule {

    @Singleton
    @Provides
    open fun provideSignInPresenter(signInInteractor: SignInContract.SignInInteractor): SignInContract.SignInPresenter {
        val presenter = SignInPresenter()
        presenter.signInInteractor = signInInteractor
        signInInteractor.onSignInListener = presenter
        return presenter
    }
}
