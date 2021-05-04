package com.codemitry.wearer.mvp.contracts.clothessubtypes

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class ClothesSubtypesContract {
    interface View : BaseContract.BaseView {
        fun showClothesTypes(clothes: List<ClothesSubtype>)
    }

    interface Presenter : BaseContract.BasePresenter<View>, OnClothesTypeAddClickListener {
        fun onClothesTypesAddClicked()
    }

    interface OnClothesTypeAddClickListener {
        fun onClothesTypeClick(clothesType: ClothesSubtype)
    }
}