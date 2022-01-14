package com.codemitry.wearer.di.components

import com.codemitry.wearer.activities.MyClothesFragment
import com.codemitry.wearer.di.modules.ClothingItemsInteractorModule
import com.codemitry.wearer.di.modules.ClothingItemsPresenterModule
import dagger.Component

@Component(modules = [ClothingItemsPresenterModule::class, ClothingItemsInteractorModule::class])
interface ClothingItemsComponent {
    fun inject(activity: MyClothesFragment)
}