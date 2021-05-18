package com.codemitry.wearer

import android.app.Application
import com.codemitry.wearer.di.delegates.*
import com.codemitry.wearer.di.impl.*
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

    override var clothesSubtypesComponentBuilder: ClothesSubtypesComponentBuilder? = null

    override var clothesType: ClothesTypesByWearingWay? = null
        set(value) {
            field = value

            value?.let {
                clothesSubtypesComponentBuilder = ClothesSubtypesComponentBuilderImpl(value)
            }
        }

    override var clothingItemsComponentBuilder: ClothingItemsComponentBuilder? = null

    override var clothesSubtype: ClothesSubtype? = null
        set(value) {
            field = value

            require(clothesType != null)
            clothesSubtype?.let {
                value?.let {
                    clothingItemsComponentBuilder = ClothingItemsComponentBuilderImpl(
                        clothesType!!,
                        value
                    )

                    addClothingItemComponentBuilder = AddClothingItemComponentBuilderImpl(
                        clothesType!!,
                        value
                    )
                }
            }
        }

    override var addClothingItemComponentBuilder: AddClothingItemComponentBuilder? = null

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }
}