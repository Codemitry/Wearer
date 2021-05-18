package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.ClothingItemsComponent
import com.codemitry.wearer.di.components.DaggerClothingItemsComponent
import com.codemitry.wearer.di.delegates.ClothingItemsComponentBuilder
import com.codemitry.wearer.di.modules.ClothingItemsInteractorModule
import com.codemitry.wearer.di.modules.ClothingItemsPresenterModule
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class ClothingItemsComponentBuilderImpl(
    override var clothesTypeByWearingWay: ClothesTypesByWearingWay,
    override var clothesSubtype: ClothesSubtype,
) : ClothingItemsComponentBuilder {


    override fun build(): ClothingItemsComponent =
        DaggerClothingItemsComponent.builder()
            .clothingItemsPresenterModule(
                ClothingItemsPresenterModule(
                    clothesTypeByWearingWay,
                    clothesSubtype
                )
            )
            .clothingItemsInteractorModule(
                ClothingItemsInteractorModule(
                    clothesTypeByWearingWay,
                    clothesSubtype
                )
            )
            .build()

}