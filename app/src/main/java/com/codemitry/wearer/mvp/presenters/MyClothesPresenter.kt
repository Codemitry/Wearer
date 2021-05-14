package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import javax.inject.Inject

class MyClothesPresenter @Inject constructor(
        override var clothesType: ClothesTypesByWearingWay,
        override var clothesSubtype: ClothesSubtype,
) : MyClothesContract.Presenter {

    override var view: MyClothesContract.View? = null

    override fun onAddClothesClick() {
        view?.showAddClothesFragment()
    }

    override fun onAskDeleteClothingItem(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onOpenClothingItemClick(clothingItem: ClothingItem) {
        // TODO
    }


}