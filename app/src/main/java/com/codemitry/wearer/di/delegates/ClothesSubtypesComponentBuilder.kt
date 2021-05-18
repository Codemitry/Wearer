package com.codemitry.wearer.di.delegates

import com.codemitry.wearer.di.components.ClothesSubtypesComponent
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface ClothesSubtypesComponentBuilder {
    var clothesTypeByWearingWay: ClothesTypesByWearingWay

    fun build(): ClothesSubtypesComponent
}