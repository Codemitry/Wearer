package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.DaggerTestSplashComponent
import com.codemitry.wearer.di.components.SplashComponent
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.di.modules.SplashPresenterModule
import com.codemitry.wearer.di.modules.TestSplashSignedInModule

class TestSplashSignedComponentBuilderImpl : SplashComponentBuilder {
    override fun build(): SplashComponent =
        DaggerTestSplashComponent.builder()
            .splashPresenterModule(SplashPresenterModule())
            .splashSignInCheckerModule(TestSplashSignedInModule())
            .build()
}