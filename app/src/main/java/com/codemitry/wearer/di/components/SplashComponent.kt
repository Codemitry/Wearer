package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.SplashActivity
import com.codemitry.wearer.di.modules.GetUserUseCaseModule
import com.codemitry.wearer.di.modules.SplashPresenterModule
import com.codemitry.wearer.di.modules.SplashSignInCheckerModule
import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashPresenterModule::class, SplashSignInCheckerModule::class, GetUserUseCaseModule::class])
interface SplashComponent {

    fun inject(activity: SplashActivity)
    fun presenter(): SplashContract.SplashPresenter
}