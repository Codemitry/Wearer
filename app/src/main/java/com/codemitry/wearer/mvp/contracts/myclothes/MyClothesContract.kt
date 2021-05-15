package com.codemitry.wearer.mvp.contracts.myclothes

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class MyClothesContract {
    interface View : BaseContract.BaseView {
        fun showAddClothesFragment()
        fun showMyClothes(myClothes: List<ClothingItem>)

        fun showClothesSubtype(clothes: ClothesSubtype)

        fun showAddedClothingItem(clothingItem: ClothingItem, position: Int)

        fun askItemDeletingConfirmation(clothingItem: ClothingItem, position: Int)

        fun showErrorLoading()
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        val clothesType: ClothesTypesByWearingWay
        val clothesSubtype: ClothesSubtype

        fun onClothingItemAdded(clothingItem: ClothingItem)
        fun onAddClothesClick()
        fun onOpenClothingItemClick(clothingItem: ClothingItem)

        fun onAskRemoveClothingItem(position: Int)
        fun onItemDeletingNegativeAnswer(clothingItem: ClothingItem, position: Int)
        fun onItemDeletingPositiveAnswer(clothingItem: ClothingItem, position: Int)
    }


    interface Interactor {
        fun saveClothingItemPhoto(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothingItemName: String,
            photoBytes: ByteArray,
            completeListener: ActionCompleteListener?,
        )

        fun saveClothingItem(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothingItem: ClothingItem,
            completeListener: ActionCompleteListener?
        )

        fun loadClothingItems(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            completeListener: ActionCompleteListener?
        )

        fun removeClothingItem(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothingItem: ClothingItem,
            completeListener: ActionCompleteListener?
        )
    }
}