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
            val clothesType: ClothesTypesByWearingWay = when (view.id) {
                R.id.outerwear -> ClothesTypesByWearingWay.OUTERWEAR
                R.id.lightClothes -> ClothesTypesByWearingWay.LIGHT_CLOTHES
                R.id.underwear -> ClothesTypesByWearingWay.UNDERWEAR
                R.id.shoes -> ClothesTypesByWearingWay.SHOES
                R.id.accessories -> ClothesTypesByWearingWay.ACCESSORIES
                else -> error("Unexpected clothes type")
            }
            ClothesSubtypesActivity.start(this, clothesType)
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