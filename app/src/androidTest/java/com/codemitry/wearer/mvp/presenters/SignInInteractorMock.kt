package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.mvp.contracts.signin.SignInContract

class SignInInteractorMock(val successSignIn: Boolean = false) : SignInContract.SignInInteractor {

    override var onSignInListener: SignInContract.SignInInteractor.OnSignInListener? = null

    override fun performFirebaseThroughGoogleSignIn(idToken: String) {
        if (successSignIn) {
            DBManager.uid = "mock_uid"
            onSignInListener?.onSuccess()
        } else {
            // Sign in fails
            onSignInListener?.onFailure()
        }
    }

    override fun performFirebaseThroughAnonymousSignIn() {
        if (successSignIn) {
            DBManager.uid = "mock_uid"
            onSignInListener?.onSuccess()
        } else {
            // Sign in fails
            onSignInListener?.onFailure()
        }
    }
}