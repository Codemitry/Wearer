package com.codemitry.wearer.app

import android.app.Application
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.di.delegates.ClothesSubtypesComponentBuilder
import com.codemitry.wearer.di.delegates.ClothingItemsComponentBuilder
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.di.impl.*
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay

class TestApp : Application(), ComponentsProvider {

    companion object {
        var signedIn: Boolean = false
        var successSignIn: Boolean = false

        var isDbManagerOk = false
    }

    override val signInComponentBuilder: SignInComponentBuilder
        get() {
            return if (successSignIn)
                TestSignInSuccessComponentBuilderImpl()
            else
                TestSignInFailComponentBuilderImpl()
        }

    override val splashComponentBuilder: SplashComponentBuilder
        get() {
            return if (signedIn)
                TestSplashSignedComponentBuilderImpl()
            else
                TestSplashNotSignedComponentBuilderImpl()
        }

    override var clothesSubtypesComponentBuilder: ClothesSubtypesComponentBuilder? = null


    override var clothingItemsComponentBuilder: ClothingItemsComponentBuilder? = null


    override var clothesType: ClothesTypesByWearingWay? = null
        set(value) {
            field = value

            clothesType?.let {
                clothesSubtypesComponentBuilder =
                    TestClothesSubtypesComponentBuilderImpl(it, isDbManagerOk)
            }
        }

    override var clothesSubtype: ClothesSubtype? = null
        set(value) {
            field = value

            clothesType?.let {
                clothingItemsComponentBuilder = TODO()
//                    TestClothesSubtypesComponentBuilderImpl(it, isDbManagerOk)
            }
        }
}