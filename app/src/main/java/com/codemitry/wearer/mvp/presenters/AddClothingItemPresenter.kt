package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.CaringLabels
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.addclothingitem.AddClothingItemContract
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import java.util.*
import javax.inject.Inject

class AddClothingItemPresenter @Inject constructor(
    private val clothesTypesByWearingWay: ClothesTypesByWearingWay,
    private val clothesSubtype: ClothesSubtype,
    private val interactor: MyClothesContract.Interactor
) : AddClothingItemContract.Presenter {

    override var view: AddClothingItemContract.View? = null

    private val selectedCaringLabels = mutableListOf<CaringLabels>()
    private var clothingItemPhoto: ByteArray? = null

    override fun onAddCaringLabelsClick() {
        val allCaringLabels = CaringLabels.values().toList()

        if (selectedCaringLabels.isEmpty()) {
            view?.showCaringLabelsPicker(allCaringLabels)
        } else {
            view?.showCaringLabelsPicker(allCaringLabels, selectedCaringLabels)
        }
    }

    override fun onPickPurchaseYearClick() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        view?.showPurchaseYearPicker(currentYear - 50, currentYear)
    }

    override fun onCaringLabelsPicked(caringLabels: List<CaringLabels>) {
        selectedCaringLabels.clear()
        selectedCaringLabels.addAll(caringLabels)
        view?.showPickedCaringLabels(selectedCaringLabels)
    }

    override fun onYearPicked(purchaseYear: Int) {
        view?.showPickedYear(purchaseYear)
    }

    override fun onCaringLabelRemoveClick(caringLabel: CaringLabels) {
        selectedCaringLabels.remove(caringLabel)
        view?.removeCaringLabel(caringLabel)
    }

    override fun onTakePhotoClick() {
        view?.takePhoto()
    }

    override fun onChooseFromGalleryClick() {
        view?.choosePhotoFromGallery()
    }

    override fun onItemClothingPhotoReady(photoBytes: ByteArray) {
        clothingItemPhoto = photoBytes
    }

    override fun onDeleteImageClick() {
        clothingItemPhoto = null
        view?.deleteImage()
    }

    override fun onAddClothingItemClick() {
        val name = view?.getItemClothingName()

        if (name == null) {
            view?.showNameIsEmptyError()
            return
        }
        if (clothingItemPhoto == null) {
            saveClothingItem(name, null)
        } else {
            interactor.saveClothingItemPhoto(
                    clothesTypesByWearingWay,
                    clothesSubtype,
                    name,
                    clothingItemPhoto!!,
                    object : ActionCompleteListener {
                        override fun onUrlAvailable(url: String) {
                            saveClothingItem(name, url)
                        }

                        override fun onFailure() {
                            view?.showErrorAddingClothingItem()
                        }
                    })
        }


    }

    private fun saveClothingItem(name: String, photoUrl: String?) {
        val clothingItem = ClothingItem(
                name,
                view?.getClothingItemBrand(),
                view?.getClothingItemSize(),
                view?.getClothingItemPurchaseYear(),
                view?.getClothingItemMaterial(),
                view?.getClothingItemNote(),
                selectedCaringLabels,
                photoUrl
        )

        interactor.saveClothingItem(
            clothesTypesByWearingWay,
            clothesSubtype,
            clothingItem,
            object : ActionCompleteListener {
                override fun onFailure() {
                    view?.showErrorAddingClothingItem()
                }

                override fun onSuccess() {
                    view?.closeFragment(clothingItem)
                }
            }
        )
    }
}

