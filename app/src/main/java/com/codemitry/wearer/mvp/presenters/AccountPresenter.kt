package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.R
import com.codemitry.wearer.models.AuthType
import com.codemitry.wearer.models.ImageResource
import com.codemitry.wearer.mvp.contracts.account.AccountContract
import com.codemitry.wearer.mvp.usecases.GetUserUseCase
import com.codemitry.wearer.mvp.usecases.SignOutUseCase
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private val signOut: SignOutUseCase,
    private val getUser: GetUserUseCase
    ): AccountContract.Presenter {

    override var view: AccountContract.View? = null

    override fun onSignOutClick() {
        signOut()
        view?.showSignInActivity()
    }

    override fun onViewAttached(view: AccountContract.View) {
        super.onViewAttached(view)

        val user = getUser()

        view.showUsername(user?.displayName ?: "")

        val userPhoto = ImageResource(user?.avatarUri, R.drawable.ic_baseline_tag_faces_24)
        view.showUserPhoto(userPhoto)
        view.showUid(user?.uid ?: "null")
        view.showAuthType(user?.authType ?: AuthType.UNKNOWN)
    }


}