package com.codemitry.wearer.di.modules

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.codemitry.wearer.mvp.presenters.ClothesSubtypesPresenter
import dagger.Module
import dagger.Provides

@Module
open class ClothesSubtypesPresenterModule(val clothesType: ClothesTypesByWearingWay) {

    @Provides
    open fun provideClothesSubtypesPresenter(
        clothesSubtypeManager: ClothesSubtypesContract.ClothesTypesManager,
    ): ClothesSubtypesContract.Presenter {
        return ClothesSubtypesPresenter(clothesType, clothesSubtypeManager)
    }
}

@Module
open class ClothesSubtypesManagerModule(val clothesType: ClothesTypesByWearingWay) {

    @Provides
    open fun provideClothesSubtypeManager(): ClothesSubtypesContract.ClothesTypesManager {
        return DBManager
    }
}