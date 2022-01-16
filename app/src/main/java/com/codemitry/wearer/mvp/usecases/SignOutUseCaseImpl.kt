package com.codemitry.wearer.mvp.usecases

import com.codemitry.wearer.db.DBManager
import com.google.firebase.auth.FirebaseAuth

class SignOutUseCaseImpl : SignOutUseCase {
    override fun invoke() {
        DBManager.user = null
        FirebaseAuth.getInstance().signOut()
    }
}