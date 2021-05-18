package com.codemitry.wearer.app

import android.app.Application
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.di.components.ClothesSubtypeComponent
import com.codemitry.wearer.di.components.ClothesTypeComponent
import com.codemitry.wearer.di.delegates.SignInComponentBuilder
import com.codemitry.wearer.di.delegates.SplashComponentBuilder
import com.codemitry.wearer.di.impl.TestSignInFailComponentBuilderImpl
import com.codemitry.wearer.di.impl.TestSignInSuccessComponentBuilderImpl
import com.codemitry.wearer.di.impl.TestSplashNotSignedComponentBuilderImpl
import com.codemitry.wearer.di.impl.TestSplashSignedComponentBuilderImpl

class TestApp : Application(), ComponentsProvider {

    companion object {
        var signedIn: Boolean = false
        var successSignIn: Boolean = false
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

    override var clothesTypeComponent: ClothesTypeComponent
        get() = TODO("Not yet implemented")
        set(value) {}
    override var clothesSubtypeComponent: ClothesSubtypeComponent
        get() = TODO("Not yet implemented")
        set(value) {}
}