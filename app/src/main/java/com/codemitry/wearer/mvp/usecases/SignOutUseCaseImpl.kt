package com.codemitry.wearer.mvp.usecases

import com.codemitry.wearer.db.DBManager
import com.google.firebase.auth.FirebaseAuth

class SignOutUseCaseImpl : SignOutUseCase {
    override fun invoke() {
        FirebaseAuth.getInstance().signOut()

        DBManager.uid = null
    }
}