package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.ClothesSubtypesComponent
import com.codemitry.wearer.di.components.DaggerClothesSubtypesComponent
import com.codemitry.wearer.di.delegates.ClothesSubtypesComponentBuilder
import com.codemitry.wearer.di.modules.ClothesSubtypesManagerModule
import com.codemitry.wearer.di.modules.ClothesSubtypesPresenterModule
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class ClothesSubtypesComponentBuilderImpl(
    override var clothesTypeByWearingWay: ClothesTypesByWearingWay
) : ClothesSubtypesComponentBuilder {


    override fun build(): ClothesSubtypesComponent =
        DaggerClothesSubtypesComponent.builder()
            .clothesSubtypesPresenterModule(ClothesSubtypesPresenterModule(clothesTypeByWearingWay))
            .clothesSubtypesManagerModule(ClothesSubtypesManagerModule(clothesTypeByWearingWay))
            .build()


}