package com.codemitry.wearer

import com.codemitry.wearer.di.components.ClothesSubtypeComponent
import com.codemitry.wearer.di.components.ClothesTypeComponent
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.delegates.SplashComponentBuilder

interface ComponentsProvider {
    val signInComponentBuilder: SignInComponentBuilder
    val splashComponentBuilder: SplashComponentBuilder

    var clothesTypeComponent: ClothesTypeComponent
    var clothesSubtypeComponent: ClothesSubtypeComponent
}