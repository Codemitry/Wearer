package com.codemitry.wearer.mvp.contracts.clothessubtypes

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class ClothesSubtypesContract {
    interface View : BaseContract.BaseView {
        fun showPossibleClothesTypes(clothes: List<ClothesSubtype>)
        fun showClothesTypes(clothes: List<ClothesSubtype>)
        fun addClothesType(clothesType: ClothesSubtype, position: Int)
        fun askItemDeletingConfirmation(item: ClothesSubtype, position: Int)
    }

    interface Presenter : BaseContract.BasePresenter<View>, OnClothesTypeAddClickListener {
        fun onClothesTypesAddClicked()
        fun onAskDeleteClothesItem(position: Int)
        fun onItemDeletingPositiveAnswer(item: ClothesSubtype, position: Int)
        fun onItemDeletingNegativeAnswer(item: ClothesSubtype, position: Int)

        fun onClothesTypeOpenClick(item: ClothesSubtype)
    }

    fun interface OnClothesTypeAddClickListener {
        fun onAddClothesTypeClick(clothesType: ClothesSubtype)
    }
}