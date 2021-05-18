package com.codemitry.wearer.di.components

import com.codemitry.wearer.di.modules.ClothesSubtypesPresenterModule
import com.codemitry.wearer.di.modules.TestClothesSubtypesManagerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ClothesSubtypesPresenterModule::class, TestClothesSubtypesManagerModule::class])
interface TestClothesSubtypesComponent : ClothesSubtypesComponent