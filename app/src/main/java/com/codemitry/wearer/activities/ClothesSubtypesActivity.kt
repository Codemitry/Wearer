package com.codemitry.wearer.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.codemitry.wearer.App
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivityClothesSubtypesBinding
import com.codemitry.wearer.fragments.ClothesSubtypesBottomFragment
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import javax.inject.Inject

class ClothesSubtypesActivity : AppCompatActivity(), ClothesSubtypesContract.View {

    private lateinit var binding: ActivityClothesSubtypesBinding
    private lateinit var clothesTypeByWearingWay: ClothesTypesByWearingWay

    @Inject
    lateinit var presenter: ClothesSubtypesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).clothesTypeComponent.inject(this)
        binding = ActivityClothesSubtypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clothesTypeByWearingWay =
            intent.getSerializableExtra(ClothesTypesByWearingWay::class.simpleName) as ClothesTypesByWearingWay
        when (clothesTypeByWearingWay) {
            ClothesTypesByWearingWay.OUTERWEAR -> {
                binding.toolbar.title = getString(R.string.outerwear)
                binding.headerImage.setImageResource(R.drawable.outerwear)
            }

            ClothesTypesByWearingWay.LIGHT_CLOTHES -> {
                binding.toolbar.title = getString(R.string.lightClothes)
                binding.headerImage.setImageResource(R.drawable.light_clothes)
            }
            ClothesTypesByWearingWay.UNDERWEAR -> {
                binding.toolbar.title = getString(R.string.underwear)
                binding.headerImage.setImageResource(R.drawable.underwear)
            }
            ClothesTypesByWearingWay.SHOES -> {
                binding.toolbar.title = getString(R.string.shoes)
                binding.headerImage.setImageResource(R.drawable.shoes)
            }
            ClothesTypesByWearingWay.ACCESSORIES -> {
                binding.toolbar.title = getString(R.string.accessories)
                binding.headerImage.setImageResource(R.drawable.accessories)
            }
        }

        binding.addClothesSubtype.setOnClickListener { presenter.onClothesTypesAddClicked() }
    }

    override fun onStart() {
        super.onStart()

        presenter.onViewAttached(this)
    }

    override fun onStop() {
        super.onStop()

        presenter.onViewDetached()
    }

    companion object {
        const val SHARED_IMAGE_NAME = "clothesSubtypeImage"
        fun start(from: Activity, clothesType: ClothesTypesByWearingWay, transitionView: View) {
            val intent = Intent(from, ClothesSubtypesActivity::class.java)
            intent.putExtra(ClothesTypesByWearingWay::class.simpleName, clothesType)

            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                from,
                transitionView,
                SHARED_IMAGE_NAME
            )

            ContextCompat.startActivity(from, intent, options.toBundle())
        }
    }


    override fun showClothesTypes(clothes: List<ClothesSubtype>) {
        val bottomFragment = ClothesSubtypesBottomFragment(presenter)
        bottomFragment.show(supportFragmentManager, ClothesSubtypesBottomFragment::class.simpleName)
        bottomFragment.showClothesTypes(clothes)
    }

}