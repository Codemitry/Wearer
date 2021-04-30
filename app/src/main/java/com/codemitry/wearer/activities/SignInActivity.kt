package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codemitry.wearer.App
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivitySignInBinding
import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

const val RC_SIGN_IN = 5112

class SignInActivity : AppCompatActivity(), SignInContract.SignInView {

    private lateinit var binding: ActivitySignInBinding

    @Inject
    lateinit var presenter: SignInContract.SignInPresenter


    override fun showSignInError() {
        Toast.makeText(this, "Failure auth", Toast.LENGTH_LONG).show()
    }

    override fun showMainActivity() {
        WearerActivity.start(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        (application as App).applicationComponent.inject(this)

        setContentView(binding.root)


        binding.anonymousSignIn.setOnClickListener { presenter.onAnonymousSignInClick() }
        binding.googleSignIn.setOnClickListener { presenter.onGoogleSignInClick() }
    }

    override fun onStart() {
        super.onStart()

        presenter.onViewAttached(this)
    }

    override fun onStop() {
        super.onStop()

        presenter.onViewDetached()
    }

    override fun startGoogleSignInFlow() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Google sign in request
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val accountIdToken =
                    task.getResult(ApiException::class.java)?.idToken ?: throw Exception()
                Log.d("Google sign in", "signInWithCredential:success")
                presenter.onGoogleSignInSuccessful(accountIdToken)

            } catch (e: Exception) {
                Log.d("Google sign in", "signInWithCredential:failure", e)
                presenter.onGoogleSignInFailure()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}