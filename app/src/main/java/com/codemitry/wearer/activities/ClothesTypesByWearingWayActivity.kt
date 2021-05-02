package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codemitry.wearer.ClothesTypesByWearingWay
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivityClothesTypesByWearingWayBinding

class ClothesTypesByWearingWayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClothesTypesByWearingWayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothesTypesByWearingWayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clickListener = { view: View ->
            val transitionView: View
            val clothesType: ClothesTypesByWearingWay
            when (view.id) {
                R.id.outerwear -> {
                    binding.outerwear.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.outerwear
                    clothesType = ClothesTypesByWearingWay.OUTERWEAR  // return value
                }
                R.id.lightClothes -> {
                    binding.lightClothes.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.lightClothes
                    clothesType = ClothesTypesByWearingWay.LIGHT_CLOTHES  // return value
                }
                R.id.underwear -> {
                    binding.underwear.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.underwear
                    clothesType = ClothesTypesByWearingWay.UNDERWEAR  // return value
                }
                R.id.shoes -> {
                    binding.shoes.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.shoes
                    clothesType = ClothesTypesByWearingWay.SHOES  // return value
                }
                R.id.accessories -> {
                    binding.accessories.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.accessories
                    clothesType = ClothesTypesByWearingWay.ACCESSORIES  // return value
                }
                else -> error("Unexpected clothes type")
            }
            ClothesSubtypesActivity.start(this, clothesType, transitionView)
        }

        binding.outerwear.setOnClickListener(clickListener)
        binding.lightClothes.setOnClickListener(clickListener)
        binding.underwear.setOnClickListener(clickListener)
        binding.shoes.setOnClickListener(clickListener)
        binding.accessories.setOnClickListener(clickListener)

    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ClothesTypesByWearingWayActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}