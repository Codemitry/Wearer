package com.codemitry.wearer.mvp.contracts.splash

import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class SplashContract {
    interface SplashView : BaseContract.BaseView {
        fun showSignInActivity()
        fun showWearerActivity()
    }

    interface SplashPresenter : BaseContract.BasePresenter<SplashView> {
        val signInChecker: SignInChecker
        fun onSplashScreenOpened()
    }

    interface SignInChecker {
        fun signedIn(): Boolean
    }

}