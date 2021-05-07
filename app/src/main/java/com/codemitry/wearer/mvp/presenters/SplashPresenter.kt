package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import javax.inject.Inject

class SplashPresenter : SplashContract.SplashPresenter {

    @Inject
    override lateinit var signInChecker: SplashContract.SignInChecker

    override var view: SplashContract.SplashView? = null

    override fun onSplashScreenOpened() {
        if (signInChecker.signedIn()) {
            DBManager.uid = signInChecker.userId()

            view?.showWearerActivity()
        } else {
            view?.showSignInActivity()
        }
    }
}