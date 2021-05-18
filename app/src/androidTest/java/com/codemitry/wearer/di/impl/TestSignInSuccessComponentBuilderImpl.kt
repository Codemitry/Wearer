package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.DaggerTestSignInComponent
import com.codemitry.wearer.di.components.SignInComponent
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.modules.SignInPresenterModule
import com.codemitry.wearer.di.modules.TestSignInSuccessModule

class TestSignInSuccessComponentBuilderImpl : SignInComponentBuilder {
    override fun build(): SignInComponent =
        DaggerTestSignInComponent.builder()
            .signInInteractorModule(TestSignInSuccessModule())
            .signInPresenterModule(SignInPresenterModule())
            .build()
}