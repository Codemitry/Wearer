package com.codemitry.wearer.di.modules

import com.codemitry.wearer.db.DBManager
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.addclothingitem.AddClothingItemContract
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.codemitry.wearer.mvp.presenters.AddClothingItemPresenter
import com.codemitry.wearer.mvp.presenters.MyClothesPresenter
import dagger.Module
import dagger.Provides

@Module
class ClothesSubtypeModule(
    val clothesType: ClothesTypesByWearingWay,
    val clothesSubtype: ClothesSubtype
) {

    @Provides
    fun provideMyClothesPresenter(): MyClothesContract.Presenter {
        return MyClothesPresenter(clothesType, clothesSubtype, DBManager)
    }

    @Provides
    fun provideAddClothingItemsPresenter(): AddClothingItemContract.Presenter {
        return AddClothingItemPresenter(clothesType, clothesSubtype, DBManager)
    }

}