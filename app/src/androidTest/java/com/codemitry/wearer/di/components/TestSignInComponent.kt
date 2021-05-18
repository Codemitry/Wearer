package com.codemitry.wearer.di.components

import com.codemitry.wearer.di.modules.SignInInteractorModule
import com.codemitry.wearer.di.modules.SignInPresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SignInPresenterModule::class, SignInInteractorModule::class])
interface TestSignInComponent : SignInComponent