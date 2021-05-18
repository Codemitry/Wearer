package com.codemitry.wearer.di.components

import com.codemitry.wearer.di.modules.AddClothingItemPresenterModule
import com.codemitry.wearer.di.modules.ClothingItemsInteractorModule
import com.codemitry.wearer.fragments.AddClothingItemFragment
import dagger.Component

@Component(modules = [AddClothingItemPresenterModule::class, ClothingItemsInteractorModule::class])
interface AddClothingItemComponent {
    fun inject(fragment: AddClothingItemFragment)
}