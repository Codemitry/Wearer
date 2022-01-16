package com.codemitry.wearer.models

import android.net.Uri

data class User(
    val uid: String,
    val authType: AuthType,
    val displayName: String? = "Trendy",
    val email: String? = null,
    val avatarUri: Uri? = null,
    )

enum class AuthType {
    GOOGLE, ANONYMOUS, UNKNOWN
}