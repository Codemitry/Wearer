package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.codemitry.wearer.mvp.presenters.SignInCheckerMock

class TestSplashSignedInModule : SplashSignInCheckerModule() {
    override fun provideSignInChecker(): SplashContract.SignInChecker {
        return SignInCheckerMock(true)
    }
}

class TestSplashNotSignedInModule : SplashSignInCheckerModule() {
    override fun provideSignInChecker(): SplashContract.SignInChecker {
        return SignInCheckerMock(false)
    }
}