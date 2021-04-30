package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.components.ApplicationComponent
import com.codemitry.wearer.di.components.DaggerApplicationComponent

class App : Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()

}