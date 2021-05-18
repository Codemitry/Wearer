package com.codemitry.wearer.di.impl

import com.codemitry.wearer.di.components.ClothesSubtypesComponent
import com.codemitry.wearer.di.components.DaggerTestClothesSubtypesComponent
import com.codemitry.wearer.di.delegates.ClothesSubtypesComponentBuilder
import com.codemitry.wearer.di.modules.ClothesSubtypesPresenterModule
import com.codemitry.wearer.di.modules.TestClothesSubtypesManagerModule
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class TestClothesSubtypesComponentBuilderImpl(
    override var clothesTypeByWearingWay: ClothesTypesByWearingWay,
    val isSuccess: Boolean = false
) : ClothesSubtypesComponentBuilder {
    override fun build(): ClothesSubtypesComponent =
        DaggerTestClothesSubtypesComponent.builder()
            .clothesSubtypesPresenterModule(ClothesSubtypesPresenterModule(clothesTypeByWearingWay))
            .testClothesSubtypesManagerModule(
                TestClothesSubtypesManagerModule(
                    clothesTypeByWearingWay,
                    isSuccess
                )
            )
            .build()
}