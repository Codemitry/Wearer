package com.codemitry.wearer.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.clothessubtypes.ClothesSubtypeItemSwipedAdapter
import com.codemitry.wearer.clothessubtypes.RecyclerItemTouchHelper
import com.codemitry.wearer.databinding.FragmentClothesSubtypesBinding
import com.codemitry.wearer.fragments.ClothesSubtypesBottomFragment
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesTypesByWearingWay
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ClothesSubtypesFragment : Fragment(), ClothesSubtypesContract.View,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private var _binding: FragmentClothesSubtypesBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var clothesTypesAdapter: ClothesSubtypeItemSwipedAdapter

    // navigation passed argument
    private val args: ClothesSubtypesFragmentArgs by navArgs()
    val clothesType: ClothesTypesByWearingWay
        get() = args.clothesType

    @Inject
    lateinit var presenter: ClothesSubtypesContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition  = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesSubtypesBinding.inflate(inflater, container, false)
        require((requireActivity().application as ComponentsProvider).clothesSubtypesComponentBuilder != null)
        (requireActivity().application as ComponentsProvider).clothesSubtypesComponentBuilder!!.build().inject(this)

        binding.addClothesSubtype.setOnClickListener { presenter.onClothesTypesAddClicked() }

        ItemTouchHelper(RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)).attachToRecyclerView(binding.clothesTypesList)

        return binding.root
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


    override fun showClothesType(clothesType: ClothesTypesByWearingWay) {
        binding.toolbar.title = getString(clothesType.nameResource)
        binding.headerImage.setImageResource(clothesType.iconResource)
    }

    override fun showPossibleClothesTypes(clothes: List<ClothesSubtype>) {
        val bottomFragment = ClothesSubtypesBottomFragment(presenter)
        bottomFragment.show(parentFragmentManager, ClothesSubtypesBottomFragment::class.simpleName)
        bottomFragment.showClothesTypes(clothes)
    }

    override fun showClothesTypes(clothes: List<ClothesSubtype>) {
        clothesTypesAdapter = ClothesSubtypeItemSwipedAdapter(clothes) { clothesType ->
            presenter.onClothesTypeOpenClick(clothesType)
        }
        binding.clothesTypesList.adapter = clothesTypesAdapter
    }

    override fun addClothesType(clothesType: ClothesSubtype, position: Int) {
        if (parentFragmentManager.fragments.isNotEmpty()) {
            val openedFragment = parentFragmentManager.fragments.last()
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
        (requireActivity().application as ComponentsProvider).clothesSubtype = clothesType
        MyClothesActivity.start(requireContext())
    }

    override fun showErrorLoading() {
        Snackbar.make(binding.root, "Error on loading", Snackbar.LENGTH_LONG).show()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        presenter.onAskDeleteClothesItem(position)
    }

    // to disable scrolling when all items are visible on a screen, and scroll is not needed
    override fun updateToolbarBehaviour() {
        binding.clothesTypesList.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom != oldBottom) {
                setToolbarScrollingEnabled(view.canScrollVertically(1) || view.canScrollVertically(-1))
            }
        }
    }

    override fun setToolbarScrollingEnabled(enabled: Boolean) {
        binding.toolbarLayout.let { toolbar ->

            toolbar.layoutParams = (toolbar.layoutParams as AppBarLayout.LayoutParams).apply {
                scrollFlags = if (enabled)
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                else
                    AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            }
        }

    }

}