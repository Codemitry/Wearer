package com.codemitry.wearer.di.modules

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.codemitry.wearer.mvp.presenters.MyClothesPresenter
import dagger.Module
import dagger.Provides

@Module
class ClothingItemsPresenterModule(
    val clothesType: ClothesTypesByWearingWay,
    val clothesSubtype: ClothesSubtype
) {

    @Provides
    fun provideMyClothesPresenter(): MyClothesContract.Presenter {
        return MyClothesPresenter(clothesType, clothesSubtype, DBManager)
    }
}

@Module
class ClothingItemsInteractorModule(
    val clothesType: ClothesTypesByWearingWay,
    val clothesSubtype: ClothesSubtype
) {

    @Provides
    fun provideMyClothesInteractor(): MyClothesContract.Interactor {
        return DBManager
    }

}