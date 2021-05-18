package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.SplashComponent

interface SplashComponentBuilder {
    fun build(): SplashComponent
}