package com.codemitry.wearer.di.modules

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.addclothingitem.AddClothingItemContract
import com.codemitry.wearer.mvp.presenters.AddClothingItemPresenter
import dagger.Module
import dagger.Provides

@Module
class AddClothingItemPresenterModule(
    val clothesType: ClothesTypesByWearingWay,
    val clothesSubtype: ClothesSubtype
) {

    @Provides
    fun provideAddClothingItemPresenter(): AddClothingItemContract.Presenter {
        return AddClothingItemPresenter(clothesType, clothesSubtype, DBManager)
    }

}