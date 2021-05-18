package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.ClothingItemsComponent
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface ClothingItemsComponentBuilder {
    var clothesTypeByWearingWay: ClothesTypesByWearingWay
    var clothesSubtype: ClothesSubtype

    fun build(): ClothingItemsComponent
}