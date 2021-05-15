package com.codemitry.wearer.mvp.presenters

import com.codemitry.wearer.db.ActionCompleteListener
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import javax.inject.Inject

class MyClothesPresenter @Inject constructor(
    override val clothesType: ClothesTypesByWearingWay,
    override val clothesSubtype: ClothesSubtype,
    val interactor: MyClothesContract.Interactor
) : MyClothesContract.Presenter {

    override var view: MyClothesContract.View? = null

    private val myClothes = mutableListOf<ClothingItem>()

    override fun onViewAttached(view: MyClothesContract.View) {
        this.view = view

        view.showClothesSubtype(clothesSubtype)

        view.showMyClothes(myClothes)

        interactor.loadClothingItems(clothesType, clothesSubtype, object : ActionCompleteListener {
            override fun onClothingItemsLoaded(clothingItems: List<ClothingItem>) {
                myClothes.clear()
                myClothes.addAll(clothingItems)

                view.showMyClothes(myClothes)
            }

            override fun onFailure() {
                // todo: show error
            }
        })
    }

    override fun onClothingItemAdded(clothingItem: ClothingItem) {
        myClothes.add(clothingItem)
        view?.showAddedClothingItem(clothingItem, myClothes.lastIndex)
    }

    override fun onAddClothesClick() {
        view?.showAddClothesFragment()
    }


    override fun onAskRemoveClothingItem(position: Int) {
        val deletedItem = myClothes[position]
        myClothes.removeAt(position)
        view?.askItemDeletingConfirmation(deletedItem, position)
    }

    override fun onOpenClothingItemClick(clothingItem: ClothingItem) {
        // TODO
    }

    override fun onItemDeletingNegativeAnswer(clothingItem: ClothingItem, position: Int) {
        myClothes.add(position, clothingItem)
        view?.showAddedClothingItem(clothingItem, position)
    }

    override fun onItemDeletingPositiveAnswer(clothingItem: ClothingItem, position: Int) {
        interactor.removeClothingItem(
            clothesType,
            clothesSubtype,
            clothingItem,
            object : ActionCompleteListener {
                override fun onSuccess() {
                    myClothes.remove(clothingItem)
                }
            })
    }
}