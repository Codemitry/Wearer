package com.codemitry.wearer.db

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

object DBManager : ClothesSubtypesContract.ClothesTypeDeleter {
    var uid: String? = null
        set(value) {
            field = value
            value?.let { value -> userRef = allUsersRef.child(value) }
        }

    private val database = Firebase.database
    private val allUsersRef = database.getReference("users")
    private lateinit var userRef: DatabaseReference

    fun clothesType(clothesTypesByWearingWay: ClothesTypesByWearingWay): DatabaseReference =
        userRef.child(clothesTypesByWearingWay.name.toLowerCase(Locale.getDefault()))

    fun clothesSubType(
        clothesSubtype: ClothesSubtype,
        clothesTypesByWearingWay: ClothesTypesByWearingWay
    ): DatabaseReference =
        clothesType(clothesTypesByWearingWay).child(
            clothesSubtype.toString().toLowerCase(Locale.getDefault())
        )

    fun deleteClothesSubtype(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesSubtype: ClothesSubtype,
        completeListener: ActionCompleteListener? = null
    ) {
        clothesSubType(clothesSubtype, clothesTypeByWearingWay).removeValue()
            .addOnSuccessListener { completeListener?.onSuccess() }
            .addOnFailureListener { completeListener?.onFailure() }
    }


    // TODO: implement
    fun addClothingItem(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesSubtype: ClothesSubtype,
        clothes: ClothingItem,
        completeListener: ActionCompleteListener? = null
    ) {
        clothesSubType(clothesSubtype, clothesTypeByWearingWay).removeValue()
            .addOnSuccessListener { completeListener?.onSuccess() }
            .addOnFailureListener { completeListener?.onFailure() }
    }


    override fun clothesSubtypeDelete(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesType: ClothesSubtype,
        actionCompleteListener: ActionCompleteListener
    ) {
        deleteClothesSubtype(clothesTypeByWearingWay, clothesType, actionCompleteListener)
    }
}

interface ActionCompleteListener {
    fun onSuccess() = Unit
    fun onFailure() = Unit
}