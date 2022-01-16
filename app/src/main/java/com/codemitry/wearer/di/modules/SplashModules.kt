package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.codemitry.wearer.mvp.presenters.SignInChecker
import com.codemitry.wearer.mvp.presenters.SplashPresenter
import com.codemitry.wearer.mvp.usecases.GetUserUseCase
import com.codemitry.wearer.mvp.usecases.GetUserUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class SplashPresenterModule {

    @Singleton
    @Provides
    open fun provideSplashPresenter(signInChecker: SplashContract.SignInChecker): SplashContract.SplashPresenter {
        val presenter = SplashPresenter()
        presenter.signInChecker = signInChecker
        return presenter
    }
}

@Module
open class SplashSignInCheckerModule {
    @Singleton
    @Provides
    open fun provideSignInChecker(getUserUseCase: GetUserUseCase): SplashContract.SignInChecker {
        return SignInChecker(getUserUseCase)
    }
}

@Module
open class GetUserUseCaseModule {
    @Singleton
    @Provides
    open fun provideGetUserUseCase(): GetUserUseCase {
        return GetUserUseCaseImpl()
    }
}

