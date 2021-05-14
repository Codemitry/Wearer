package com.codemitry.wearer.mvp.contracts.addclothingitem

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.CaringLabels
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class AddClothingItemContract {
    interface View : BaseContract.BaseView {
        fun showCaringLabelsPicker(caringLabels: List<CaringLabels>)
        fun showCaringLabelsPicker(caringLabels: List<CaringLabels>, selectedCaringLabels: List<CaringLabels>)
        fun showPurchaseYearPicker(fromYear: Int, toYear: Int)

        fun showPickedCaringLabels(caringLabels: List<CaringLabels>)
        fun showPickedYear(pickedYear: Int)

        fun removeCaringLabel(caringLabel: CaringLabels)

        fun takePhoto()
        fun choosePhotoFromGallery()

        fun deleteImage()

        fun getItemClothingName(): String?
        fun getClothingItemBrand(): String?
        fun getClothingItemSize(): String?
        fun getClothingItemPurchaseYear(): Int?
        fun getClothingItemMaterial(): String?
        fun getClothingItemNote(): String?

        fun showNameIsEmptyError()
        fun showErrorAddingClothingItem()

        fun closeFragment()

    }

    interface Presenter : BaseContract.BasePresenter<View> {
        fun onAddCaringLabelsClick()
        fun onCaringLabelsPicked(caringLabels: List<CaringLabels>)

        fun onPickPurchaseYearClick()
        fun onYearPicked(purchaseYear: Int)

        fun onCaringLabelRemoveClick(caringLabel: CaringLabels)

        fun onTakePhotoClick()
        fun onChooseFromGalleryClick()
        fun onItemClothingPhotoReady(photoBytes: ByteArray)

        fun onDeleteImageClick()

        fun onAddClothingItemClick()
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
    }

}