package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.codemitry.wearer.mvp.usecases.GetUserUseCase

class SignInChecker(
    private val userUseCase: GetUserUseCase
) : SplashContract.SignInChecker {
    override fun signedIn(): Boolean {
        return userUseCase() != null
    }
}