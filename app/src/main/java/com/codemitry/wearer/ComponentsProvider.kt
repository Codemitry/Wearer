package com.codemitry.wearer

import com.codemitry.wearer.di.delegates.*
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface ComponentsProvider {
    val signInComponentBuilder: SignInComponentBuilder
    val splashComponentBuilder: SplashComponentBuilder

    var clothesSubtypesComponentBuilder: ClothesSubtypesComponentBuilder?

    var clothingItemsComponentBuilder: ClothingItemsComponentBuilder?
    var addClothingItemComponentBuilder: AddClothingItemComponentBuilder?

    var clothesType: ClothesTypesByWearingWay?
    var clothesSubtype: ClothesSubtype?
}