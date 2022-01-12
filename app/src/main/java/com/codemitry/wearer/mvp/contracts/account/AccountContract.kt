package com.codemitry.wearer.mvp.contracts.account

import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class AccountContract {
    interface View : BaseContract.BaseView {
        fun showSignInActivity()
    }

    interface Presenter : BaseContract.BasePresenter<View> {

        fun onSignOutClick()
    }

}