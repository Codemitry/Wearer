package com.codemitry.wearer.tests.signin

import com.codemitry.wearer.mocks.SignInInteractorMock
import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.codemitry.wearer.mvp.presenters.SignInPresenter
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PresenterTest {

    private class ViewMock : SignInContract.SignInView {

        var shouldShowSignInErrorCalled = false
        var shouldShowMainActivityCalled = false
        var shouldStartGoogleSignInFlowCalled = false

        override fun showSignInError() {
            assertTrue(shouldShowSignInErrorCalled)
        }

        override fun showMainActivity() {
            assertTrue(shouldShowMainActivityCalled)

        }

        override fun startGoogleSignInFlow() {
            assertTrue(shouldStartGoogleSignInFlowCalled)
        }
    }

    private val presenter = SignInPresenter()
    private val view = ViewMock()
    private val signInInteractor = SignInInteractorMock(false)

    init {
        presenter.onViewAttached(view)
        presenter.signInInteractor = signInInteractor
        presenter.signInInteractor.onSignInListener = presenter
    }

    @Before
    fun init() {
        view.shouldStartGoogleSignInFlowCalled = false
        view.shouldShowMainActivityCalled = false
        view.shouldShowSignInErrorCalled = false

        signInInteractor.successSignIn = false
    }

    @Test
    fun googleSuccessSignIn() {
        signInInteractor.successSignIn = true

        view.shouldStartGoogleSignInFlowCalled = true

        presenter.onGoogleSignInClick()

        view.shouldStartGoogleSignInFlowCalled = false
        view.shouldShowMainActivityCalled = true

        presenter.onGoogleSignInSuccessful("mock")
    }

    @Test
    fun googleFailureFirstSignIn() {
        view.shouldStartGoogleSignInFlowCalled = true

        presenter.onGoogleSignInClick()

        view.shouldStartGoogleSignInFlowCalled = false
        view.shouldShowSignInErrorCalled = true
    }

    @Test
    fun googleFailureSecondSignIn() {
        signInInteractor.successSignIn = true
        view.shouldStartGoogleSignInFlowCalled = true

        presenter.onGoogleSignInClick()

        view.shouldStartGoogleSignInFlowCalled = false

        signInInteractor.successSignIn = false

        view.shouldShowSignInErrorCalled = true
        presenter.onGoogleSignInSuccessful("mock")

    }


    @Test
    fun anonSuccessSignIn() {
        signInInteractor.successSignIn = true

        view.shouldShowMainActivityCalled = true

        presenter.onAnonymousSignInClick()
    }

    @Test
    fun anonFailureSignIn() {
        view.shouldShowSignInErrorCalled = true

        presenter.onAnonymousSignInClick()
    }
}