package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.mvp.contracts.account.AccountContract
import com.codemitry.wearer.mvp.usecases.SignOutUseCase
import javax.inject.Inject

class AccountPresenter @Inject constructor(private val signOut: SignOutUseCase): AccountContract.Presenter {

    override var view: AccountContract.View? = null

    override fun onSignOutClick() {
        signOut()
        view?.showSignInActivity()
    }


}