package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codemitry.wearer.App
import com.codemitry.wearer.databinding.ActivityMyClothesBinding
import com.codemitry.wearer.fragments.AddClothingItemFragment
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.codemitry.wearer.myclothes.MyClothesItemSwipedAdapter
import javax.inject.Inject

class MyClothesActivity : AppCompatActivity(), MyClothesContract.View {


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
        val fragment = AddClothingItemFragment()
        (application as App).clothesSubtypeComponent.inject(fragment)
        fragment.show(supportFragmentManager, AddClothingItemFragment::class.simpleName)
    }

    override fun showMyClothes(myClothes: List<ClothingItem>) {
        myClothesAdapter = MyClothesItemSwipedAdapter(myClothes) { clothingItem ->
            presenter.onOpenClothingItemClick(clothingItem)
        }
        binding.myClothesList.adapter = myClothesAdapter
    }

    override fun addClothingItem(clothingItem: ClothingItem, position: Int) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val openedFragment = supportFragmentManager.fragments.last()
            if (openedFragment is AddClothingItemFragment)
                openedFragment.dismiss()
        }
        myClothesAdapter.notifyItemInserted(position)
    }
}