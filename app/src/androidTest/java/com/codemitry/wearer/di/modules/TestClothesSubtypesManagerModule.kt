package com.codemitry.wearer.di.modules

import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.codemitry.wearer.mvp.presenters.ClothesSubtypesManagerMock
import dagger.Module
import dagger.Provides

@Module
class TestClothesSubtypesManagerModule(
    clothesType: ClothesTypesByWearingWay,
    val isSuccess: Boolean = false
) {

    @Provides
    fun provideClothesSubtypeManager(): ClothesSubtypesContract.ClothesTypesManager {
        return ClothesSubtypesManagerMock(isSuccess)
    }

}