package com.codemitry.wearer.di.components

import com.codemitry.wearer.di.modules.SplashPresenterModule
import com.codemitry.wearer.di.modules.SplashSignInCheckerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashSignInCheckerModule::class, SplashPresenterModule::class])
interface TestSplashComponent : SplashComponent