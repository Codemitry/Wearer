package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.models.*
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract

class ClothesSubtypesPresenter(val clothesType: ClothesTypesByWearingWay) :
    ClothesSubtypesContract.Presenter {

    override var view: ClothesSubtypesContract.View? = null

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
        view?.showClothesTypes(clothes)
    }


    override fun onClothesTypeClick(clothesType: ClothesSubtype) {
    }
}