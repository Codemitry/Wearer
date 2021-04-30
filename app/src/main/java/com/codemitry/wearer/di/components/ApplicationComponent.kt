package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.SignInActivity
import com.codemitry.wearer.activities.SplashActivity
import com.codemitry.wearer.di.modules.ApplicationModule
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: SignInActivity)
    fun inject(activity: SplashActivity)

}