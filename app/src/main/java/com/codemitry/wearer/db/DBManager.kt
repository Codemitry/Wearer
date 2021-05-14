package com.codemitry.wearer.db

import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.models.clothesSubtypeByName
import com.codemitry.wearer.mvp.contracts.addclothingitem.AddClothingItemContract
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

object DBManager : ClothesSubtypesContract.ClothesTypesManager, AddClothingItemContract.Interactor {
    var uid: String? = null
        set(value) {
            field = value
            value?.let { value -> userRef = allUsersRef.child(value); userStorageRef = allUsersStorageRef.child(value) }
        }

    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private val allUsersStorageRef = storageRef.child("users")
    lateinit var userStorageRef: StorageReference

    private val database = Firebase.database("https://wearer-7b20e-default-rtdb.europe-west1.firebasedatabase.app/")
    private val allUsersRef = database.getReference("users")
    private lateinit var userRef: DatabaseReference

    private val clothesSubtypesName = "clothes_subtypes"
    private val clothingItemsName = "clothing_items"


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


    private fun getClothesSubtypesImpl(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            completeListener: ActionCompleteListener? = null
    ) {
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

    override fun saveClothingItemPhoto(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothingItemName: String,
            photoBytes: ByteArray,
            completeListener: ActionCompleteListener?
    ) {
        val photoRef =
                userStorageRef.child(clothesTypeByWearingWay.toString().toLowerCase(Locale.getDefault()))
                        .child(clothesSubtype.toString().toLowerCase(Locale.getDefault()))
                        .child(clothingItemName.toLowerCase(Locale.getDefault()))
        photoRef.putBytes(photoBytes)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    photoRef.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        completeListener?.onSuccess()
                        downloadUri?.path?.let { completeListener?.onUrlAvailable(it) }
                    } else {
                        completeListener?.onFailure()
                    }
                }
    }

    override fun saveClothingItem(
            clothesTypeByWearingWay: ClothesTypesByWearingWay,
            clothesSubtype: ClothesSubtype,
            clothingItem: ClothingItem,
            completeListener: ActionCompleteListener?
    ) {
        userRef.child(clothingItemsName).child(clothesTypeByWearingWay.name.toLowerCase(Locale.getDefault()))
                .child(clothesSubtype.toString().toLowerCase(Locale.getDefault()))
                .child(clothingItem.name)
                .setValue(clothingItem)
                .addOnSuccessListener { completeListener?.onSuccess() }
                .addOnFailureListener() { completeListener?.onFailure() }
    }
}

interface ActionCompleteListener {
    fun onSuccess() = Unit
    fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) = Unit
    fun onFailure() = Unit
    fun onUrlAvailable(url: String) = Unit
}