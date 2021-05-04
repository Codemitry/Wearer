package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.components.ApplicationComponent
import com.codemitry.wearer.di.components.ClothesTypeComponent
import com.codemitry.wearer.di.components.DaggerApplicationComponent
import com.codemitry.wearer.di.components.DaggerClothesTypeComponent
import com.codemitry.wearer.di.modules.ClothesTypeModule
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class App : Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()
    lateinit var clothesTypeComponent: ClothesTypeComponent
    fun setClothesType(clothesType: ClothesTypesByWearingWay) {
        clothesTypeComponent =
            DaggerClothesTypeComponent.builder().clothesTypeModule(ClothesTypeModule(clothesType))
                .build()
    }

}