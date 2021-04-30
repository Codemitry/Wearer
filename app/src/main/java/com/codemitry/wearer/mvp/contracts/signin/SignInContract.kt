package com.codemitry.wearer.mvp.contracts.signin

import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class SignInContract {
    interface SignInView : BaseContract.BaseView {
        fun showSignInError()

        fun showMainActivity()

        fun startGoogleSignInFlow()
    }

    interface SignInPresenter : BaseContract.BasePresenter<SignInView> {

        fun onGoogleSignInClick()
        fun onAnonymousSignInClick()
        fun onGoogleSignInSuccessful(idToken: String)
        fun onGoogleSignInFailure()
    }

    interface SignInInteractor {
        interface OnSignInListener {
            fun onSuccess()
            fun onFailure()
        }

        var onSignInListener: OnSignInListener?

        fun performFirebaseThroughGoogleSignIn(idToken: String)
        fun performFirebaseThroughAnonymousSignIn()

    }
}