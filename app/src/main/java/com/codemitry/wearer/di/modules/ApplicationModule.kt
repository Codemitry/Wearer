package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.codemitry.wearer.mvp.presenters.SignInChecker
import com.codemitry.wearer.mvp.presenters.SignInInteractor
import com.codemitry.wearer.mvp.presenters.SignInPresenter
import com.codemitry.wearer.mvp.presenters.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideSignInPresenter(signInInteractor: SignInContract.SignInInteractor): SignInContract.SignInPresenter {
        val presenter = SignInPresenter()
        signInInteractor.onSignInListener = presenter
        presenter.signInInteractor = signInInteractor
        return presenter
    }

    @Provides
    fun provideSignInInteractor(): SignInContract.SignInInteractor {
        return SignInInteractor()
    }

    @Provides
    fun provideSplashPresenter(signInChecker: SplashContract.SignInChecker): SplashContract.SplashPresenter {
        val presenter = SplashPresenter()
        presenter.signInChecker = signInChecker
        return presenter
    }

    @Provides
    fun provideSignInChecker(): SplashContract.SignInChecker {
        return SignInChecker()
    }

}