package com.codemitry.wearer.db

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.models.clothesSubtypeByName
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

object DBManager : ClothesSubtypesContract.ClothesTypesManager {
    var uid: String? = null
        set(value) {
            field = value
            value?.let { value -> userRef = allUsersRef.child(value) }
        }

    private val database = Firebase.database("https://wearer-7b20e-default-rtdb.europe-west1.firebasedatabase.app/")
    private val allUsersRef = database.getReference("users")
    private lateinit var userRef: DatabaseReference

    private val clothesSubtypesName = "clothes_subtypes"

    private fun clothesSubtype(clothesTypesByWearingWay: ClothesTypesByWearingWay): DatabaseReference =
            userRef.child(clothesSubtypesName).child(clothesTypesByWearingWay.name.toLowerCase(Locale.getDefault()))

    private fun clothesSubType(
            clothesSubtype: ClothesSubtype,
            clothesTypesByWearingWay: ClothesTypesByWearingWay
    ): DatabaseReference =
            clothesSubtype(clothesTypesByWearingWay).child(
                    clothesSubtype.toString().toLowerCase(Locale.getDefault())
            )

    private fun deleteClothesSubtypeImpl(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            completeListener: ActionCompleteListener? = null
    ) {
        userRef.child(clothesSubtypesName).child(
                clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())
        ).child(clothesSubtype.toString().toLowerCase(Locale.getDefault())).removeValue()
                .addOnSuccessListener { completeListener?.onSuccess() }
                .addOnFailureListener { completeListener?.onFailure() }
    }


    // TODO: implement
    private fun addClothingItem(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothes: ClothingItem,
            completeListener: ActionCompleteListener? = null
    ) {
        clothesSubType(clothesSubtype, clothesTypeByWearingWay).removeValue()
                .addOnSuccessListener { completeListener?.onSuccess() }
                .addOnFailureListener { completeListener?.onFailure() }
    }

    private fun getClothesSubtypesImpl(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            completeListener: ActionCompleteListener? = null
    ) {
        println(userRef.child(clothesSubtypesName).child(clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())).key)
        userRef.child(clothesSubtypesName).child(clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result?.value != null) {
                        val clothesSubtypes = mutableListOf<ClothesSubtype>()

                        for (clothesSubtypeSnapshot in task.result?.children ?: emptyList()) {
                            clothesSubtypes.add(clothesSubtypeByName(clothesTypeByWearingWay, clothesSubtypeSnapshot.key!!))
                        }
                        completeListener?.onClothesSubtypesLoaded(clothesSubtypes)
                    } else {
                        completeListener?.onFailure()
                    }
                }
    }

    private fun addClothesSubtypeImpl(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            completeListener: ActionCompleteListener? = null
    ) {
        userRef.child(clothesSubtypesName).child(
                clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())
        ).child(clothesSubtype.toString().toLowerCase(Locale.getDefault())).setValue(true)
                .addOnSuccessListener { completeListener?.onSuccess() }
                .addOnFailureListener { completeListener?.onFailure() }
    }


    override fun deleteClothesSubtype(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesType: ClothesSubtype,
            actionCompleteListener: ActionCompleteListener
    ) {
        deleteClothesSubtypeImpl(clothesTypeByWearingWay, clothesType, actionCompleteListener)
    }

    override fun loadClothesSubtypes(clothesTypeByWearingWay: ClothesTypesByWearingWay, completeListener: ActionCompleteListener) {
        getClothesSubtypesImpl(clothesTypeByWearingWay, completeListener)
    }

    override fun addClothesSubtype(clothesTypeByWearingWay: ClothesTypesByWearingWay, clothesType: ClothesSubtype, actionCompleteListener: ActionCompleteListener) {
        addClothesSubtypeImpl(clothesTypeByWearingWay, clothesType, actionCompleteListener)
    }
}

interface ActionCompleteListener {
    fun onSuccess() = Unit
    fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) = Unit
    fun onFailure() = Unit
}