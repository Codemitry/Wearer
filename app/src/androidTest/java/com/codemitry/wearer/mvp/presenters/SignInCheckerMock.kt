package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.mvp.contracts.splash.SplashContract

class SignInCheckerMock(val signedIn: Boolean = false) : SplashContract.SignInChecker {


    override fun signedIn(): Boolean {
        return signedIn
    }

    override fun userId(): String? {
        return if (signedIn) "mocked_uid" else null
    }
}