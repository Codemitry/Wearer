package com.codemitry.wearer.db

import com.codemitry.wearer.models.*
import com.codemitry.wearer.mvp.contracts.LoadingIndicatorView
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

object DBManager : ClothesSubtypesContract.ClothesTypesManager, MyClothesContract.Interactor {
    var user: User? = null
    set(value) {
        field = value
        value?.let {
            userRef = allUsersRef.child(it.uid); userStorageRef = allUsersStorageRef.child(it.uid)
        }
    }

    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private val allUsersStorageRef = storageRef.child("users")
    lateinit var userStorageRef: StorageReference

    private val database =
        Firebase.database("https://wearer-7b20e-default-rtdb.europe-west1.firebasedatabase.app/")
    private val allUsersRef = database.getReference("users")
    private lateinit var userRef: DatabaseReference

    private val clothesSubtypesName = "clothes_subtypes"
    private val clothingItemsName = "clothing_items"

    private fun deleteFolderContent(folderPath: StorageReference) {
        folderPath.listAll().addOnSuccessListener {
            it.items.forEach { item ->
                folderPath.child(item.name).delete()
            }

            it.prefixes.forEach { folder ->
                deleteFolderContent(folder)
            }

        }
    }

    private fun deleteClothesSubtypeImpl(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesSubtype: ClothesSubtype,
        completeListener: ActionCompleteListener? = null
    ) {

        userRef.child(clothingItemsName)
            .child(clothesTypeByWearingWay.name.lowercase(Locale.getDefault()))
            .child(clothesSubtype.toString().lowercase(Locale.getDefault()))
            .removeValue()

        val subtypesRef =
            userStorageRef.child(
                clothesTypeByWearingWay.toString().lowercase(Locale.getDefault())
            )
                .child(clothesSubtype.toString().lowercase(Locale.getDefault()))

        deleteFolderContent(subtypesRef)

        userRef.child(clothesSubtypesName).child(
            clothesTypeByWearingWay.name.lowercase(Locale.getDefault())
        ).child(clothesSubtype.toString().lowercase(Locale.getDefault())).removeValue()
            .addOnSuccessListener { completeListener?.onSuccess() }
            .addOnFailureListener { completeListener?.onFailure() }
    }


    private fun getClothesSubtypesImpl(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        completeListener: ActionCompleteListener? = null
    ) {
        userRef.child(clothesSubtypesName)
            .child(clothesTypeByWearingWay.name.lowercase(Locale.getDefault())).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val clothesSubtypes = mutableListOf<ClothesSubtype>()

                    for (clothesSubtypeSnapshot in task.result?.children ?: emptyList()) {
                        clothesSubtypes.add(
                            clothesSubtypeByName(
                                clothesTypeByWearingWay,
                                clothesSubtypeSnapshot.key!!
                            )
                        )
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
            clothesTypeByWearingWay.name.lowercase(Locale.getDefault())
        ).child(clothesSubtype.toString().lowercase(Locale.getDefault())).setValue(true)
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

    override fun loadClothesSubtypes(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        completeListener: ActionCompleteListener
    ) {
        getClothesSubtypesImpl(clothesTypeByWearingWay, completeListener)
    }

    override fun addClothesSubtype(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesType: ClothesSubtype,
        actionCompleteListener: ActionCompleteListener
    ) {
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
            userStorageRef.child(
                clothesTypeByWearingWay.toString().lowercase(Locale.getDefault())
            )
                .child(clothesSubtype.toString().lowercase(Locale.getDefault()))
                .child(clothingItemName.lowercase(Locale.getDefault()))

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
                    completeListener?.onUrlAvailable(downloadUri?.toString()!!) //}
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
        userRef.child(clothingItemsName)
            .child(clothesTypeByWearingWay.name.lowercase(Locale.getDefault()))
            .child(clothesSubtype.toString().lowercase(Locale.getDefault()))
            .child(clothingItem.name)
            .setValue(clothingItem)
            .addOnSuccessListener { completeListener?.onSuccess() }
            .addOnFailureListener { completeListener?.onFailure() }
    }

    override fun removeClothingItem(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesSubtype: ClothesSubtype,
        clothingItem: ClothingItem,
        completeListener: ActionCompleteListener?
    ) {
        if (clothingItem.photoUrl != null) {
            userStorageRef.child(
                clothesTypeByWearingWay.toString().lowercase(Locale.getDefault())
            )
                .child(clothesSubtype.toString().lowercase(Locale.getDefault()))
                .child(clothingItem.name)
                .delete()

        }
        userRef.child(clothingItemsName)
            .child(clothesTypeByWearingWay.name.lowercase(Locale.getDefault()))
            .child(clothesSubtype.toString().lowercase(Locale.getDefault()))
            .child(clothingItem.name)
            .removeValue()
            .addOnSuccessListener { completeListener?.onSuccess() }
            .addOnFailureListener { completeListener?.onFailure() }
    }

    override fun loadClothingItems(
        clothesTypeByWearingWay: ClothesTypesByWearingWay,
        clothesSubtype: ClothesSubtype,
        completeListener: ActionCompleteListener?
    ) {

        userRef.child(clothingItemsName)
            .child(clothesTypeByWearingWay.name.lowercase(Locale.getDefault()))
            .child(clothesSubtype.toString().lowercase(Locale.getDefault())).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val clothingItems = mutableListOf<ClothingItem>()

                    for (clothingItemSnapshot in task.result?.children ?: emptyList()) {
                        val caringLabels = mutableListOf<CaringLabels>()

                        for (caringLabel in clothingItemSnapshot.child("caringLabels").children) {
                            caringLabels.add(
                                CaringLabels.valueOf(
                                    caringLabel.value.toString().uppercase(Locale.getDefault())
                                )
                            )
                        }

                        val item = ClothingItem(
                            clothingItemSnapshot.child("name").value.toString(),
                            clothingItemSnapshot.child("brand").value?.toString(),
                            clothingItemSnapshot.child("size").value?.toString(),
                            clothingItemSnapshot.child("purchaseYear").value?.toString()?.toInt(),
                            clothingItemSnapshot.child("material").value?.toString(),
                            clothingItemSnapshot.child("notes").value?.toString(),
                            if (caringLabels.isEmpty()) null else caringLabels,
                            clothingItemSnapshot.child("photoUrl").value?.toString(),

                            )

                        clothingItems.add(item)
                    }
                    completeListener?.onClothingItemsLoaded(clothingItems)
                } else {
                    completeListener?.onFailure()
                }
            }
    }

    fun deleteAccount(completeListener: ActionCompleteListener) {
        val dataDeleteTask = userRef.removeValue()

        userStorageRef.delete().continueWith {
            if (it.isComplete && !it.isSuccessful) {
                completeListener.onFailure()
            }

            dataDeleteTask
        }.addOnSuccessListener { completeListener.onSuccess() }
            .addOnFailureListener { completeListener.onFailure() }
    }
}

interface ActionCompleteListener {
    fun onSuccess() = Unit
    fun onClothesSubtypesLoaded(clothesSubtypes: List<ClothesSubtype>) = Unit
    fun onClothingItemsLoaded(clothingItems: List<ClothingItem>) = Unit
    fun onFailure() = Unit
    fun onUrlAvailable(url: String) = Unit
}