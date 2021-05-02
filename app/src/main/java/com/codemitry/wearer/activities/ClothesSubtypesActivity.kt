package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codemitry.wearer.ClothesTypesByWearingWay
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivityClothesSubtypesBinding

class ClothesSubtypesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClothesSubtypesBinding
    private lateinit var clothesTypeByWearingWay: ClothesTypesByWearingWay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothesSubtypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clothesTypeByWearingWay = intent.getSerializableExtra(ClothesTypesByWearingWay::class.simpleName) as ClothesTypesByWearingWay
        binding.toolbar.title = when (clothesTypeByWearingWay) {
            ClothesTypesByWearingWay.OUTERWEAR -> getString(R.string.outerwear)
            ClothesTypesByWearingWay.LIGHT_CLOTHES -> getString(R.string.lightClothes)
            ClothesTypesByWearingWay.UNDERWEAR -> getString(R.string.underwear)
            ClothesTypesByWearingWay.SHOES -> getString(R.string.shoes)
            ClothesTypesByWearingWay.ACCESSORIES -> getString(R.string.accessories)
        }

        binding.headerImage.setImageResource(when (clothesTypeByWearingWay) {
            ClothesTypesByWearingWay.OUTERWEAR -> R.drawable.outerwear
            ClothesTypesByWearingWay.LIGHT_CLOTHES -> R.drawable.light_clothes
            ClothesTypesByWearingWay.UNDERWEAR -> R.drawable.underwear
            ClothesTypesByWearingWay.SHOES -> R.drawable.shoes
            ClothesTypesByWearingWay.ACCESSORIES -> R.drawable.accessories
        })
    }

    companion object {
        fun start(context: Context, clothesType: ClothesTypesByWearingWay) {
            val intent = Intent(context, ClothesSubtypesActivity::class.java)
            intent.putExtra(ClothesTypesByWearingWay::class.simpleName, clothesType)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}