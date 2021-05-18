package com.codemitry.wearer.tests.splash

import com.codemitry.wearer.mvp.contracts.splash.SplashContract
import com.codemitry.wearer.mvp.presenters.SplashPresenter
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PresenterTest {

    private class ViewMock : SplashContract.SplashView {

        var shouldShowSignInActivityCalled = false
        var shouldShowWearerActivityCalled = false

        override fun showSignInActivity() {
            assertTrue(shouldShowSignInActivityCalled)
        }

        override fun showWearerActivity() {
            assertTrue(shouldShowWearerActivityCalled)
        }
    }

    private class SignInCheckerMock : SplashContract.SignInChecker {
        var signedIn = false

        override fun signedIn(): Boolean {
            return signedIn
        }

        override fun saveUserId(uid: String) = Unit

        override fun userId(): String? = if (signedIn) "" else null
    }

    private val presenter = SplashPresenter()
    private val view = ViewMock()
    private val signInChecker = SignInCheckerMock()

    init {
        presenter.onViewAttached(view)
        presenter.signInChecker = signInChecker
    }

    @Before
    fun init() {
        signInChecker.signedIn = false
        view.shouldShowSignInActivityCalled = false
        view.shouldShowWearerActivityCalled = false
    }

    @Test
    fun signedIn() {
        signInChecker.signedIn = true
        view.shouldShowWearerActivityCalled = true

        presenter.onSplashScreenOpened()
    }

    @Test
    fun notSignedIn() {
        view.shouldShowSignInActivityCalled = true

        presenter.onSplashScreenOpened()
    }
}