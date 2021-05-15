package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.App
import com.codemitry.wearer.R
import com.codemitry.wearer.clothessubtypes.RecyclerItemTouchHelper
import com.codemitry.wearer.databinding.ActivityMyClothesBinding
import com.codemitry.wearer.fragments.AddClothingItemFragment
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.codemitry.wearer.myclothes.MyClothesItemSwipedAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MyClothesActivity : AppCompatActivity(), MyClothesContract.View,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    @Inject
    lateinit var presenter: MyClothesContract.Presenter
    private lateinit var binding: ActivityMyClothesBinding

    private lateinit var myClothesAdapter: MyClothesItemSwipedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).clothesSubtypeComponent.inject(this)

        binding = ActivityMyClothesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMyClothes.setOnClickListener { presenter.onAddClothesClick() }

        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.myClothesList)
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
        fun start(context: Context) {
            val intent = Intent(context, MyClothesActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun showAddClothesFragment() {
        val fragment = AddClothingItemFragment { item ->
            presenter.onClothingItemAdded(item)
        }
        (application as App).clothesSubtypeComponent.inject(fragment)
        fragment.show(supportFragmentManager, AddClothingItemFragment::class.simpleName)
    }

    override fun showAddedClothingItem(clothingItem: ClothingItem, position: Int) {
        (binding.myClothesList.adapter as? MyClothesItemSwipedAdapter)?.addItem(
            clothingItem,
            position
        )
    }

    override fun showMyClothes(myClothes: List<ClothingItem>) {
        myClothesAdapter = MyClothesItemSwipedAdapter(myClothes) { clothingItem ->
            presenter.onOpenClothingItemClick(clothingItem)
        }
        binding.myClothesList.adapter = myClothesAdapter
    }

    override fun showClothesSubtype(clothes: ClothesSubtype) {
        binding.toolbar.title = getString(clothes.nameResource)
        binding.headerImage.setImageResource(clothes.iconResource)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            presenter.onAskRemoveClothingItem(position)
        }
    }

    override fun askItemDeletingConfirmation(clothingItem: ClothingItem, position: Int) {
        (binding.myClothesList.adapter as? MyClothesItemSwipedAdapter)?.removeItem(clothingItem)

        Snackbar.make(
            binding.root,
            getString(R.string.itemWillBeRemoved, clothingItem.name),
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.undo) {
                presenter.onItemDeletingNegativeAnswer(
                    clothingItem,
                    position
                )
            }
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)

                    if (event == DISMISS_EVENT_TIMEOUT) {
                        presenter.onItemDeletingPositiveAnswer(clothingItem, position)
                    }
                }
            })
            .show()
    }
}