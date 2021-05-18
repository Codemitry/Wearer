package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.AddClothingItemComponent
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface AddClothingItemComponentBuilder {
    var clothesTypeByWearingWay: ClothesTypesByWearingWay
    var clothesSubtype: ClothesSubtype

    fun build(): AddClothingItemComponent
}