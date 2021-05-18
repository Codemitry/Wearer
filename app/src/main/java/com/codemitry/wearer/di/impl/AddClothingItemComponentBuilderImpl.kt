package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.AddClothingItemComponent
import com.codemitry.wearer.di.components.DaggerAddClothingItemComponent
import com.codemitry.wearer.di.delegates.AddClothingItemComponentBuilder
import com.codemitry.wearer.di.modules.AddClothingItemPresenterModule
import com.codemitry.wearer.di.modules.ClothingItemsInteractorModule
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class AddClothingItemComponentBuilderImpl(
    override var clothesTypeByWearingWay: ClothesTypesByWearingWay,
    override var clothesSubtype: ClothesSubtype,
) : AddClothingItemComponentBuilder {


    override fun build(): AddClothingItemComponent =
        DaggerAddClothingItemComponent.builder()
            .addClothingItemPresenterModule(
                AddClothingItemPresenterModule(clothesTypeByWearingWay, clothesSubtype)
            )
            .clothingItemsInteractorModule(
                ClothingItemsInteractorModule(
                    clothesTypeByWearingWay,
                    clothesSubtype
                )
            )
            .build()

}