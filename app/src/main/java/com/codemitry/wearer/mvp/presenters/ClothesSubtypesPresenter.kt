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

        view.showClothesType(clothesType)
        view.showClothesTypes(userClothesTypes)

        view.updateToolbarBehaviour()
    }


    private var isItemDeletingOperationRunning = false
    set(value) {
        field = value
        if (!value) deletingClothes = null
    }

    private var deletingClothes: ClothesSubtype? = null

    override fun onViewDetached() {
        super.onViewDetached()

        if (isItemDeletingOperationRunning) {

            clothesTypesManager.deleteClothesSubtype(
                clothesType,
                deletingClothes!!,
                object : ActionCompleteListener {
                    override fun onSuccess() {
                        isItemDeletingOperationRunning = false
                        userClothesTypes.remove(deletingClothes)
                    }

                    override fun onFailure() {
                        isItemDeletingOperationRunning = false
                    }
                })
        }
    }

    init {
        clothesTypesManager.loadClothesSubtypes(clothesType, object : ActionCompleteListener {
            override fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) {
                userClothesTypes.clear()
                userClothesTypes.addAll(clothesSubtypes)

                view?.showClothesTypes(userClothesTypes)
                view?.updateToolbarBehaviour()
            }

            override fun onFailure() {
                view?.showErrorLoading()
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
        deletingClothes = userClothesTypes[position]
        userClothesTypes.removeAt(position)
        isItemDeletingOperationRunning = true
        view?.askItemDeletingConfirmation(deletingClothes!!, position)
    }

    override fun onItemDeletingPositiveAnswer(item: ClothesSubtype, position: Int) {
        clothesTypesManager.deleteClothesSubtype(
                clothesType,
                item,
                object : ActionCompleteListener {
                    override fun onSuccess() {
                        isItemDeletingOperationRunning = false
                        userClothesTypes.remove(item)
                        view?.updateToolbarBehaviour()
                    }

                    override fun onFailure() {
                        isItemDeletingOperationRunning = false
                    }
                })
    }

    override fun onItemDeletingNegativeAnswer(item: ClothesSubtype, position: Int) {
        isItemDeletingOperationRunning = false
        userClothesTypes.add(position, item)
        view?.addClothesType(item, position)
        view?.updateToolbarBehaviour()

    }

    override fun onClothesTypeOpenClick(item: ClothesSubtype) {
        view?.showMyClothesActivity(item)
    }


    override fun onAddClothesTypeClick(clothesType: ClothesSubtype) {
        clothesTypesManager.addClothesSubtype(
            this.clothesType,
            clothesType,
            object : ActionCompleteListener {
                override fun onSuccess() {
                    userClothesTypes.add(clothesType)
                    view?.addClothesType(clothesType, userClothesTypes.lastIndex)
                    view?.updateToolbarBehaviour()
                }

                override fun onFailure() {
                    view?.showErrorLoading()
                }
            })
    }
}