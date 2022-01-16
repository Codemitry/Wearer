package com.codemitry.wearer.mvp.usecases

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.AuthType
import com.codemitry.wearer.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GetUserUseCaseImpl : GetUserUseCase {
    override fun invoke(): User? {
        return DBManager.user ?: Firebase.auth.currentUser?.let { fbUser ->
            val user = if (fbUser.isAnonymous) User(
                fbUser.uid,
                AuthType.ANONYMOUS,
            ) else User(
                fbUser.uid,
                AuthType.GOOGLE,
                fbUser.displayName,
                fbUser.email,
                fbUser.photoUrl
            )
            DBManager.user = user
            user
        }


    }
}