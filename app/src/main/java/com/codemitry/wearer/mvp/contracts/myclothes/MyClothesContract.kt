package com.codemitry.wearer.mvp.contracts.myclothes

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.base.BaseContract

abstract class MyClothesContract {
    interface View : BaseContract.BaseView {
    }

    interface Presenter : BaseContract.BasePresenter<View> {
        val clothesType: ClothesTypesByWearingWay
        val clothesSubtype: ClothesSubtype
    }

}