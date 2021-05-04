package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.ClothesSubtypesActivity
import com.codemitry.wearer.di.modules.ClothesTypeModule
import dagger.Component

@Component(modules = [ClothesTypeModule::class])
interface ClothesTypeComponent {
    fun inject(activity: ClothesSubtypesActivity)
}