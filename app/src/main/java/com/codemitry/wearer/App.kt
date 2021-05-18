package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.components.ClothesSubtypeComponent
import com.codemitry.wearer.di.components.ClothesTypeComponent
import com.codemitry.wearer.di.components.DaggerClothesSubtypeComponent
import com.codemitry.wearer.di.components.DaggerClothesTypeComponent
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.di.impl.SignInComponentBuilderImpl
import com.codemitry.wearer.di.impl.SplashComponentBuilderImpl
import com.codemitry.wearer.di.modules.ClothesSubtypeModule
import com.codemitry.wearer.di.modules.ClothesTypeModule
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App : Application(), ComponentsProvider {

    override val signInComponentBuilder: SignInComponentBuilder by lazy {
        SignInComponentBuilderImpl()
    }
    override val splashComponentBuilder: SplashComponentBuilder by lazy {
        SplashComponentBuilderImpl()
    }

    override lateinit var clothesTypeComponent: ClothesTypeComponent
    private lateinit var clothesTypeModule: ClothesTypeModule
    override lateinit var clothesSubtypeComponent: ClothesSubtypeComponent
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