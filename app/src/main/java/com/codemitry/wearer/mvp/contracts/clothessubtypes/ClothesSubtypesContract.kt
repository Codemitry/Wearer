package com.codemitry.wearer.mvp.contracts.clothessubtypes

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class ClothesSubtypesContract {
    interface View : BaseContract.BaseView {
        fun showClothesType(clothesType: ClothesTypesByWearingWay)
        fun showPossibleClothesTypes(clothes: List<ClothesSubtype>)
        fun showClothesTypes(clothes: List<ClothesSubtype>)
        fun addClothesType(clothesType: ClothesSubtype, position: Int)
        fun askItemDeletingConfirmation(item: ClothesSubtype, position: Int)
        fun showMyClothesActivity(clothesType: ClothesSubtype)

        fun showErrorLoading()

        fun setToolbarScrollingEnabled(enabled: Boolean)
        fun updateToolbarBehaviour()
    }

    interface Presenter : BaseContract.BasePresenter<View>, OnClothesTypeAddClickListener {
        val clothesType: ClothesTypesByWearingWay

        fun onClothesTypesAddClicked()
        fun onAskDeleteClothesItem(position: Int)
        fun onItemDeletingPositiveAnswer(item: ClothesSubtype, position: Int)
        fun onItemDeletingNegativeAnswer(item: ClothesSubtype, position: Int)

        fun onClothesTypeOpenClick(item: ClothesSubtype)
    }

    fun interface OnClothesTypeAddClickListener {
        fun onAddClothesTypeClick(clothesType: ClothesSubtype)
    }

    interface ClothesTypesManager {
        fun loadClothesSubtypes(clothesTypeByWearingWay: ClothesTypesByWearingWay, completeListener: ActionCompleteListener)

        fun deleteClothesSubtype(
                clothesTypeByWearingWay: ClothesTypesByWearingWay,
                clothesType: ClothesSubtype,
                actionCompleteListener: ActionCompleteListener
        )

        fun addClothesSubtype(
                clothesTypeByWearingWay: ClothesTypesByWearingWay,
                clothesType: ClothesSubtype,
                actionCompleteListener: ActionCompleteListener
        )
    }
}