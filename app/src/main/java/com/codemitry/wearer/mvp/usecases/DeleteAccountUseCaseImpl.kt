package com.codemitry.wearer.mvp.usecases

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.db.DBManager

class DeleteAccountUseCaseImpl : DeleteAccountUseCase {

    override fun invoke(completeListener: ActionCompleteListener) {
        DBManager.deleteAccount(completeListener)
    }
}