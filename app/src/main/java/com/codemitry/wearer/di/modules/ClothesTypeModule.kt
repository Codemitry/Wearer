package com.codemitry.wearer.di.modules

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.codemitry.wearer.mvp.presenters.ClothesSubtypesPresenter
import dagger.Module
import dagger.Provides

@Module
class ClothesTypeModule(val clothesType: ClothesTypesByWearingWay) {

    @Provides
    fun provideClothesSubtypesPresenter(
            clothesSubtypeDeleter: ClothesSubtypesContract.ClothesTypeDeleter,
            clothesSubtypesLoader: ClothesSubtypesContract.ClothesTypesLoader
    ): ClothesSubtypesContract.Presenter {
        return ClothesSubtypesPresenter(clothesType, clothesSubtypeDeleter, clothesSubtypesLoader)
    }

    @Provides
    fun provideClothesSubtypeDeleter(): ClothesSubtypesContract.ClothesTypeDeleter {
        return DBManager
    }

    @Provides
    fun provideClothesSubtypeLoader(): ClothesSubtypesContract.ClothesTypesLoader {
        return DBManager
    }
}