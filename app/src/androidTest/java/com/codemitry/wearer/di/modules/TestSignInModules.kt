package com.codemitry.wearer.di.modules

import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import com.codemitry.wearer.mvp.presenters.SignInInteractorMock

class TestSignInSuccessModule : SignInInteractorModule() {

    override fun provideSignInInteractor(): SignInContract.SignInInteractor {
        return SignInInteractorMock(true)
    }
}


class TestSignInFailModule : SignInInteractorModule() {
    override fun provideSignInInteractor(): SignInContract.SignInInteractor {
        return SignInInteractorMock(false)
    }
}
