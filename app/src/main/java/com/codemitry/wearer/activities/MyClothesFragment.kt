package com.codemitry.wearer.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.clothessubtypes.RecyclerItemTouchHelper
import com.codemitry.wearer.databinding.FragmentMyClothesBinding
import com.codemitry.wearer.fragments.AddClothingItemFragment
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.myclothes.MyClothesContract
import com.codemitry.wearer.myclothes.MyClothesItemSwipedAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MyClothesFragment : Fragment(), MyClothesContract.View,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    @Inject
    lateinit var presenter: MyClothesContract.Presenter

    private var _binding: FragmentMyClothesBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var myClothesAdapter: MyClothesItemSwipedAdapter


    override val toolbarLayout: CollapsingToolbarLayout
        get() = binding.toolbarLayout
    override val list: RecyclerView
        get() = binding.myClothesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition  = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyClothesBinding.inflate(inflater, container, false)

        require((requireActivity().application as ComponentsProvider).clothingItemsComponentBuilder != null)
        (requireActivity().application as ComponentsProvider).clothingItemsComponentBuilder!!.build().inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun showAddClothesFragment() {
        val fragment = AddClothingItemFragment { item ->
            presenter.onClothingItemAdded(item)
        }
        require((requireActivity().application as ComponentsProvider).addClothingItemComponentBuilder != null)
        (requireActivity().application as ComponentsProvider).addClothingItemComponentBuilder?.build()
            ?.inject(fragment)
        fragment.show(childFragmentManager, AddClothingItemFragment::class.simpleName)
    }

    override fun showAddedClothingItem(clothingItem: ClothingItem, position: Int) {
        (binding.myClothesList.adapter as? MyClothesItemSwipedAdapter)?.addItem(
            clothingItem,
            position
        )
    }

    override fun showErrorLoading() {
        Snackbar.make(binding.root, "Error on loading", Snackbar.LENGTH_LONG).show()
    }

    override fun showClothingItem(clothingItem: ClothingItem, clothesSubtype: ClothesSubtype) {
        ClothingItemActivity.start(requireContext(), clothingItem, clothesSubtype)
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