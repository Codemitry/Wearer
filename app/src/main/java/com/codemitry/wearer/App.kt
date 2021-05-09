package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.components.*
import com.codemitry.wearer.di.modules.ClothesSubtypeModule
import com.codemitry.wearer.di.modules.ClothesTypeModule
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App : Application() {

    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()
    lateinit var clothesTypeComponent: ClothesTypeComponent
    private lateinit var clothesTypeModule: ClothesTypeModule
    lateinit var clothesSubtypeComponent: ClothesSubtypeComponent
    private lateinit var clothesSubtypeModule: ClothesSubtypeModule

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }

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