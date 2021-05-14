package com.codemitry.wearer.mvp.contracts.myclothes

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class MyClothesContract {
    interface View : BaseContract.BaseView {
        fun showAddClothesFragment()
        fun showMyClothes(myClothes: List<ClothingItem>)
        fun addClothingItem(clothingItem: ClothingItem, position: Int)
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        val clothesType: ClothesTypesByWearingWay
        val clothesSubtype: ClothesSubtype

        fun onAddClothesClick()
        fun onAskDeleteClothingItem(position: Int)
        fun onOpenClothingItemClick(clothingItem: ClothingItem)
    }

}