package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInChecker : SplashContract.SignInChecker {
    override fun signedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}