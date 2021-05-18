package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.codemitry.wearer.mvp.presenters.SignInInteractor
import com.codemitry.wearer.mvp.presenters.SignInPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class SignInInteractorModule {

    @Singleton
    @Provides
    open fun provideSignInInteractor(): SignInContract.SignInInteractor {
        return SignInInteractor()
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
