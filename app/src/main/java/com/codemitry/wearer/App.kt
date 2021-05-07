package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.components.*
import com.codemitry.wearer.di.modules.ClothesSubtypeModule
import com.codemitry.wearer.di.modules.ClothesTypeModule
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class App : Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()
    lateinit var clothesTypeComponent: ClothesTypeComponent
    private lateinit var clothesTypeModule: ClothesTypeModule
    lateinit var clothesSubtypeComponent: ClothesSubtypeComponent
    private lateinit var clothesSubtypeModule: ClothesSubtypeModule

    fun setClothesType(clothesType: ClothesTypesByWearingWay) {
        clothesTypeModule = ClothesTypeModule(clothesType)
        clothesTypeComponent =
            DaggerClothesTypeComponent.builder().clothesTypeModule(clothesTypeModule)
                .build()
    }

    fun setClothesSubtype(clothesType: ClothesSubtype) {
        clothesSubtypeModule = ClothesSubtypeModule(clothesTypeModule.clothesType, clothesType)
        clothesSubtypeComponent =
            DaggerClothesSubtypeComponent.builder().clothesSubtypeModule(clothesSubtypeModule)
                .build()
    }

}