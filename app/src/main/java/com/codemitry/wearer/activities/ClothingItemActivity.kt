package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codemitry.wearer.clothingitems.CaringLabelsAdapter
import com.codemitry.wearer.databinding.ActivityClothingItemBinding
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothingItem
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.stfalcon.imageviewer.StfalconImageViewer

class ClothingItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClothingItemBinding

    private lateinit var clothingItem: ClothingItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClothingItemBinding.inflate(layoutInflater)

        setContentView(binding.root)

        clothingItem = intent.getSerializableExtra(clothingItemKey) as ClothingItem

        binding.clothingSubtype.text = getString((intent.getIntExtra(clothesSubtypeNameRes, 0)))

        binding.name.editText?.setText(clothingItem.name)
        if (clothingItem.brand == null)
            binding.brand.visibility = View.GONE
        else
            binding.brand.editText?.setText(clothingItem.brand)

        if (clothingItem.size == null)
            binding.size.visibility = View.GONE
        else
            binding.size.editText?.setText(clothingItem.size)

        if (clothingItem.purchaseYear == null)
            binding.purchaseYear.visibility = View.GONE
        else
            binding.purchaseYear.editText?.setText(clothingItem.purchaseYear.toString())

        if (clothingItem.material == null)
            binding.material.visibility = View.GONE
        else
            binding.material.editText?.setText(clothingItem.material)

        if (clothingItem.notes == null)
            binding.note.visibility = View.GONE
        else
            binding.note.editText?.setText(clothingItem.notes)

        if (clothingItem.caringLabels == null) {
            binding.caringLabelsTitle.visibility = View.GONE
            binding.caringLabelsList.visibility = View.GONE
        } else {
            binding.caringLabelsList.adapter = CaringLabelsAdapter(clothingItem.caringLabels!!)
        }

        if (clothingItem.photoUrl == null) {
            binding.photo.visibility = View.GONE
        }

        clothingItem.photoUrl?.let { photoUrl ->

            Glide.with(this)
                .load(Firebase.storage.getReferenceFromUrl(photoUrl))
                .into(binding.photo)


            binding.photo.setOnClickListener {
                StfalconImageViewer.Builder(this, listOf(photoUrl)) { view, image ->
                    Glide.with(this)
                        .load(Firebase.storage.getReferenceFromUrl(image))
                        .into(view)
                }.withTransitionFrom(binding.photo).show()
            }
        }

        binding.close.setOnClickListener { finish() }

    }

    companion object {
        const val clothingItemKey = "clothingItem"
        const val clothesSubtypeNameRes = "clothesSubtype"
        fun start(context: Context, clothingItem: ClothingItem, clothesSubtype: ClothesSubtype) {
            val intent = Intent(context, ClothingItemActivity::class.java)
            intent.putExtra(clothingItemKey, clothingItem)
            intent.putExtra(clothesSubtypeNameRes, clothesSubtype.nameResource)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}