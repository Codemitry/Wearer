package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.DaggerSignInComponent
import com.codemitry.wearer.di.components.SignInComponent
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.modules.SignInInteractorModule
import com.codemitry.wearer.di.modules.SignInPresenterModule

class SignInComponentBuilderImpl : SignInComponentBuilder {
    override fun build(): SignInComponent =
        DaggerSignInComponent.builder()
            .signInPresenterModule(SignInPresenterModule())
            .signInInteractorModule(SignInInteractorModule())
            .build()
}