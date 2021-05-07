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
    fun provideClothesSubtypesPresenter(clothesSubtypeDeleter: ClothesSubtypesContract.ClothesTypeDeleter): ClothesSubtypesContract.Presenter {
        return ClothesSubtypesPresenter(clothesType, clothesSubtypeDeleter)
    }

    @Provides
    fun provideClothesSubtypeDeleter(): ClothesSubtypesContract.ClothesTypeDeleter {
        return DBManager
    }
}