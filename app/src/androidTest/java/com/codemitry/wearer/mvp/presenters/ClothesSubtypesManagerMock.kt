package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract

class ClothesSubtypesManagerMock(var success: Boolean = false) :
    ClothesSubtypesContract.ClothesTypesManager {

    val clothes = mutableListOf<ClothesSubtype>()

    override fun loadClothesSubtypes(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        completeListener: ActionCompleteListener
    ) {
        if (success)
            completeListener.onClothesSubtypesLoaded(clothes)
        else
            completeListener.onFailure()
    }

    override fun deleteClothesSubtype(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesType: ClothesSubtype,
        actionCompleteListener: ActionCompleteListener
    ) {
        if (success) {
            clothes.remove(clothesType)
            actionCompleteListener.onSuccess()
        } else
            actionCompleteListener.onFailure()
    }

    override fun addClothesSubtype(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesType: ClothesSubtype,
        actionCompleteListener: ActionCompleteListener
    ) {
        if (success) {
            clothes.add(clothesType)
            actionCompleteListener.onSuccess()
        } else
            actionCompleteListener.onFailure()
    }
}