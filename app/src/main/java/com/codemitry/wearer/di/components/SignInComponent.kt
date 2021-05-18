package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.SignInActivity
import com.codemitry.wearer.di.modules.SignInInteractorModule
import com.codemitry.wearer.di.modules.SignInPresenterModule
import com.codemitry.wearer.mvp.contracts.signin.SignInContract
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SignInInteractorModule::class, SignInPresenterModule::class])
interface SignInComponent {

    fun inject(activity: SignInActivity)

    fun presenter(): SignInContract.SignInPresenter

}