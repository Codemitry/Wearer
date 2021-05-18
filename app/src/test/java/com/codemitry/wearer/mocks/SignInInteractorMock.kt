package com.codemitry.wearer.mocks

import com.codemitry.wearer.mvp.contracts.signin.SignInContract

class SignInInteractorMock(var successSignIn: Boolean = false) : SignInContract.SignInInteractor {

    override var onSignInListener: SignInContract.SignInInteractor.OnSignInListener? = null

    override fun performFirebaseThroughGoogleSignIn(idToken: String) {
        if (successSignIn) {
            onSignInListener?.onSuccess()
        } else {
            // Sign in fails
            onSignInListener?.onFailure()
        }
    }

    override fun performFirebaseThroughAnonymousSignIn() {
        if (successSignIn) {
            onSignInListener?.onSuccess()
        } else {
            // Sign in fails
            onSignInListener?.onFailure()
        }
    }
}