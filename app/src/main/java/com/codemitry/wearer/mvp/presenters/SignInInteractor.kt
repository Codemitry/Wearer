package com.codemitry.wearer.mvp.presenters

import android.util.Log
import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class SignInInteractor @Inject constructor() : SignInContract.SignInInteractor {

    override var onSignInListener: SignInContract.SignInInteractor.OnSignInListener? = null

    override fun performFirebaseThroughGoogleSignIn(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("Sign in", "signInWithCredential:success")
                    onSignInListener?.onSuccess()
                } else {
                    // Sign in fails
                    Log.w("Sign in", "signInWithCredential:failure", task.exception)
                    onSignInListener?.onFailure()
                }
            }
    }

    override fun performFirebaseThroughAnonymousSignIn() {
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("Sign in", "signInAnonymously:success")
                    onSignInListener?.onSuccess()
                } else {
                    // Sign in fails
                    Log.d("Sign in", "signInAnonymously:failure", task.exception)
                    onSignInListener?.onFailure()
                }
            }
    }
}