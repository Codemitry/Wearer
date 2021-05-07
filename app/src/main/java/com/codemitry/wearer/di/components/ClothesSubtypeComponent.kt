package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.MyClothesActivity
import com.codemitry.wearer.di.modules.ClothesSubtypeModule
import dagger.Component

@Component(modules = [ClothesSubtypeModule::class])
interface ClothesSubtypeComponent {
    fun inject(activity: MyClothesActivity)
}