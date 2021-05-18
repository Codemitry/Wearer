package com.codemitry.wearer

import com.codemitry.wearer.di.delegates.ClothesSubtypesComponentBuilder
import com.codemitry.wearer.di.delegates.ClothingItemsComponentBuilder
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface ComponentsProvider {
    val signInComponentBuilder: SignInComponentBuilder
    val splashComponentBuilder: SplashComponentBuilder

    var clothesSubtypesComponentBuilder: ClothesSubtypesComponentBuilder?

    var clothingItemsComponentBuilder: ClothingItemsComponentBuilder?

    var clothesType: ClothesTypesByWearingWay?
    var clothesSubtype: ClothesSubtype?
}