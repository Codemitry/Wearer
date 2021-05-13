package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.*
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import javax.inject.Inject

class ClothesSubtypesPresenter @Inject constructor(
        override var clothesType: ClothesTypesByWearingWay,
        var clothesTypesManager: ClothesSubtypesContract.ClothesTypesManager,
) : ClothesSubtypesContract.Presenter {

    override var view: ClothesSubtypesContract.View? = null

    private var userClothesTypes = mutableListOf<ClothesSubtype>()

    override fun onViewAttached(view: ClothesSubtypesContract.View) {
        this.view = view

        view.showClothesTypes(userClothesTypes)
    }

    init {
        clothesTypesManager.loadClothesSubtypes(clothesType, object : ActionCompleteListener {
            override fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) {
                userClothesTypes.clear()
                userClothesTypes.addAll(clothesSubtypes)

                view?.showClothesTypes(userClothesTypes)
            }

            override fun onFailure() {
                // TODO: on failure view
            }
        })
    }

    override fun onClothesTypesAddClicked() {
        val clothes = mutableListOf<ClothesSubtype>()

        when (clothesType) {
            ClothesTypesByWearingWay.OUTERWEAR -> {
                clothes.addAll(OuterwearTypes.values())
            }
            ClothesTypesByWearingWay.LIGHT_CLOTHES -> {
                clothes.addAll(LightClothesTypes.values())
            }
            ClothesTypesByWearingWay.UNDERWEAR -> {
                clothes.addAll(UnderwearTypes.values())
            }
            ClothesTypesByWearingWay.SHOES -> {
                clothes.addAll(ShoesTypes.values())
            }
            ClothesTypesByWearingWay.ACCESSORIES -> {
                clothes.addAll(AccessoriesTypes.values())
            }
        }
        // remove duplicates in possible clothes subtypes
        clothes.removeAll(userClothesTypes)
        view?.showPossibleClothesTypes(clothes)
    }

    override fun onAskDeleteClothesItem(position: Int) {
        val deletedItem = userClothesTypes[position]
        userClothesTypes.removeAt(position)
        view?.askItemDeletingConfirmation(deletedItem, position)
    }

    override fun onItemDeletingPositiveAnswer(item: ClothesSubtype, position: Int) {
        clothesTypesManager.deleteClothesSubtype(
                clothesType,
                item,
                object : ActionCompleteListener {
                    override fun onSuccess() {
                        userClothesTypes.remove(item)
                    }
                })
    }

    override fun onItemDeletingNegativeAnswer(item: ClothesSubtype, position: Int) {
        userClothesTypes.add(position, item)
        view?.addClothesType(item, position)

    }

    override fun onClothesTypeOpenClick(item: ClothesSubtype) {
        view?.showMyClothesActivity(item)
    }


    override fun onAddClothesTypeClick(clothesType: ClothesSubtype) {
        clothesTypesManager.addClothesSubtype(this.clothesType, clothesType, object : ActionCompleteListener {
            override fun onSuccess() {
                userClothesTypes.add(clothesType)
                view?.addClothesType(clothesType, userClothesTypes.lastIndex)
            }
        })
    }
}