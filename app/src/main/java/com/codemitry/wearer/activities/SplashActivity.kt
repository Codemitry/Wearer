package com.codemitry.wearer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.databinding.ActivitySplashBinding
import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), SplashContract.SplashView {

    @Inject
    lateinit var presenter: SplashContract.SplashPresenter

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ComponentsProvider).splashComponentBuilder.build().inject(this)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        presenter.onViewAttached(this)
        presenter.onSplashScreenOpened()
    }

    override fun onStop() {
        super.onStop()

        presenter.onViewDetached()
    }

    override fun showSignInActivity() {
        SignInActivity.start(this)
        finish()
    }

    override fun showWearerActivity() {
        ClothesTypesByWearingWayActivity.start(this)
        finish()
    }

}