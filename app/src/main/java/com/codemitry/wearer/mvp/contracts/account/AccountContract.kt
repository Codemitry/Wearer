package com.codemitry.wearer.mvp.contracts.account

import com.codemitry.wearer.models.AuthType
import com.codemitry.wearer.models.ImageResource
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class AccountContract {
    interface View : BaseContract.BaseView {
        fun showSignInActivity()

        fun showUserPhoto(image: ImageResource)
        fun showUsername(username: String)
        fun showUid(uid: String)
        fun showAuthType(authType: AuthType)
        fun showDialogDeleteAccountConfirmation(onConfirm: () -> Unit, onDismiss: () -> Unit)

        fun showErrorDeletingAccountMessage()
        fun showAccountDeletedSuccessfullyMessage()
    }

    interface Presenter : BaseContract.BasePresenter<View> {

        fun onSignOutClick()
        fun onDeleteAccountClick()
    }

}