package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import javax.inject.Inject

class SignInPresenter : SignInContract.SignInPresenter,
    SignInContract.SignInInteractor.OnSignInListener {
    override var view: SignInContract.SignInView? = null

    @Inject
    override lateinit var signInInteractor: SignInContract.SignInInteractor

    override fun onSuccess() {
        view?.showMainActivity()
    }

    override fun onFailure() {
        view?.showSignInError()
    }

    override fun onGoogleSignInClick() {
        view?.startGoogleSignInFlow()
    }


    override fun onAnonymousSignInClick() {
        signInInteractor.performFirebaseThroughAnonymousSignIn()
    }

    override fun onGoogleSignInSuccessful(idToken: String) {
        signInInteractor.performFirebaseThroughGoogleSignIn(idToken)
    }

    override fun onGoogleSignInFailure() {
        view?.showSignInError()
    }
}