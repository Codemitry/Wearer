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

object DBManager : ClothesSubtypesContract.ClothesTypeDeleter, ClothesSubtypesContract.ClothesTypesLoader {
    var uid: String? = null
        set(value) {
            field = value
            value?.let { value -> userRef = allUsersRef.child(value) }
        }

    private val database = Firebase.database("https://wearer-7b20e-default-rtdb.europe-west1.firebasedatabase.app/")
    private val allUsersRef = database.getReference("users")
    private lateinit var userRef: DatabaseReference

    private val clothesSubtypesName = "clothes_subtypes"

    fun clothesSubtype(clothesTypesByWearingWay: ClothesTypesByWearingWay): DatabaseReference =
            userRef.child(clothesSubtypesName).child(clothesTypesByWearingWay.name.toLowerCase(Locale.getDefault()))

    fun clothesSubType(
            clothesSubtype: ClothesSubtype,
            clothesTypesByWearingWay: ClothesTypesByWearingWay
    ): DatabaseReference =
            clothesSubtype(clothesTypesByWearingWay).child(
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

    fun getClothesSubtypes(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            completeListener: ActionCompleteListener? = null
    ) {
        println(userRef.child(clothesSubtypesName).child(clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())).key)
        userRef.child(clothesSubtypesName).child(clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault())).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val clothesSubtypes = mutableListOf<ClothesSubtype>()

                        for (clothesSubtype in task.result?.value as List<*>) {
                            clothesSubtype as String
                            clothesSubtypes.add(clothesSubtypeByName(clothesTypeByWearingWay, clothesSubtype))
                        }
                        completeListener?.onClothesSubtypesLoaded(clothesSubtypes)
                    } else {
                        completeListener?.onFailure()
                    }
                }
    }


    override fun clothesSubtypeDelete(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesType: ClothesSubtype,
            actionCompleteListener: ActionCompleteListener
    ) {
        deleteClothesSubtype(clothesTypeByWearingWay, clothesType, actionCompleteListener)
    }

    override fun loadClothesSubtypes(clothesTypeByWearingWay: ClothesTypesByWearingWay, completeListener: ActionCompleteListener) {
        getClothesSubtypes(clothesTypeByWearingWay, completeListener)
    }
}

interface ActionCompleteListener {
    fun onSuccess() = Unit
    fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) = Unit
    fun onFailure() = Unit
}