package com.codemitry.wearer.mvp.usecases

import android.view.View
import com.codemitry.wearer.models.ClothesTypesByWearingWay

interface MoveToClothesSubtypesUseCase : (MoveToClothesSubtypesUseCaseParams) -> Unit {
}

data class MoveToClothesSubtypesUseCaseParams(
    val selectedClothesType: ClothesTypesByWearingWay,
    val transitionView: View
)