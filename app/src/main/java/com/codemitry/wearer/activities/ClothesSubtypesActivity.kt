package com.codemitry.wearer.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.App
import com.codemitry.wearer.R
import com.codemitry.wearer.clothessubtypes.ClothesSubtypeItemSwipedAdapter
import com.codemitry.wearer.clothessubtypes.RecyclerItemTouchHelper
import com.codemitry.wearer.databinding.ActivityClothesSubtypesBinding
import com.codemitry.wearer.fragments.ClothesSubtypesBottomFragment
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ClothesSubtypesActivity : AppCompatActivity(), ClothesSubtypesContract.View,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var binding: ActivityClothesSubtypesBinding
    private lateinit var clothesTypeByWearingWay: ClothesTypesByWearingWay

    private lateinit var clothesTypesAdapter: ClothesSubtypeItemSwipedAdapter

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

        clothesTypesAdapter = ClothesSubtypeItemSwipedAdapter(emptyList()) { clothesType ->
            presenter.onClothesTypeOpenClick(clothesType)
        }
        binding.clothesTypesList.adapter = clothesTypesAdapter

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.clothesTypesList)
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


    override fun showPossibleClothesTypes(clothes: List<ClothesSubtype>) {
        val bottomFragment = ClothesSubtypesBottomFragment(presenter)
        bottomFragment.show(supportFragmentManager, ClothesSubtypesBottomFragment::class.simpleName)
        bottomFragment.showClothesTypes(clothes)
    }

    override fun showClothesTypes(clothes: List<ClothesSubtype>) {
        clothesTypesAdapter = ClothesSubtypeItemSwipedAdapter(clothes) { clothesType ->
            presenter.onClothesTypeOpenClick(clothesType)
        }
        binding.clothesTypesList.adapter = clothesTypesAdapter
    }

    override fun addClothesType(clothesType: ClothesSubtype, position: Int) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val openedFragment = supportFragmentManager.fragments.last()
            if (openedFragment is ClothesSubtypesBottomFragment)
                openedFragment.dismiss()
        }
        clothesTypesAdapter.notifyItemInserted(position)
    }

    override fun askItemDeletingConfirmation(item: ClothesSubtype, position: Int) {
        clothesTypesAdapter.notifyItemRemoved(position)
        clothesTypesAdapter.notifyItemRangeChanged(position, clothesTypesAdapter.itemCount)

        Snackbar.make(
            binding.root,
            getString(R.string.itemWillBeRemoved, getString(item.nameResource)),
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.undo) { presenter.onItemDeletingNegativeAnswer(item, position) }
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)

                    if (event == DISMISS_EVENT_TIMEOUT) {
                        presenter.onItemDeletingPositiveAnswer(item, position)
                    }
                }
            })
                .show()

    }

    override fun showMyClothesActivity(clothesType: ClothesSubtype) {
        (application as App).setClothesSubtype(clothesType)
        MyClothesActivity.start(this)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        presenter.onAskDeleteClothesItem(position)
    }

}