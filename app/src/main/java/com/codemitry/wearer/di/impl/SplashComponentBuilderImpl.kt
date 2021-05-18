package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.DaggerSplashComponent
import com.codemitry.wearer.di.components.SplashComponent
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.di.modules.SplashPresenterModule
import com.codemitry.wearer.di.modules.SplashSignInCheckerModule

class SplashComponentBuilderImpl : SplashComponentBuilder {
    override fun build(): SplashComponent =
        DaggerSplashComponent.builder()
            .splashPresenterModule(SplashPresenterModule())
            .splashSignInCheckerModule(SplashSignInCheckerModule())
            .build()
}