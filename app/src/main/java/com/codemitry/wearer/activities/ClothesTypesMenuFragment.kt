package com.codemitry.wearer.activities

import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentClothesTypesMenuBinding
import com.codemitry.wearer.models.ClothesTypesByWearingWay


class ClothesTypesMenuFragment : Fragment() {

    private var _binding: FragmentClothesTypesMenuBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    @IdRes private var transitionViewId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesTypesMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (transitionViewId != 0) {
            view.findViewById<View>(transitionViewId).transitionName = getString(R.string.clothingTypeTransition)
        }

        val clickListener = { view: View ->
            val transitionView: View
            val clothesType = when (view.id) {
                R.id.outerwear -> {
                    transitionView = binding.outerwearImg

                    ClothesTypesByWearingWay.OUTERWEAR  // return value
                }
                R.id.lightClothes -> {
                    transitionView = binding.lightClothesImg
                    ClothesTypesByWearingWay.LIGHT_CLOTHES  // return value
                }
                R.id.underwear -> {
                    transitionView = binding.underwearImg
                    ClothesTypesByWearingWay.UNDERWEAR  // return value
                }
                R.id.shoes -> {
                    transitionView = binding.shoesImg
                    ClothesTypesByWearingWay.SHOES  // return value
                }
                R.id.accessories -> {
                    transitionView = binding.accessoriesImg
                    ClothesTypesByWearingWay.ACCESSORIES  // return value
                }
                else -> error("Unexpected clothes type")
            }
            if (transitionViewId != 0) {
                requireActivity().findViewById<View>(transitionViewId).transitionName = null
            }
            transitionViewId = transitionView.id

            transitionView.transitionName = getString(R.string.clothingTypeTransition)

            (requireActivity().application as ComponentsProvider).clothesType = clothesType

            val transitionDetails = ClothesSubtypesFragment(clothesType)

            val Transition = object : TransitionSet() {
                init {
                    this.ordering = ORDERING_TOGETHER
                    this.addTransition(ChangeBounds())
                        .addTransition(ChangeTransform())
                        .addTransition(ChangeImageTransform())
                }
            }

            transitionDetails.sharedElementEnterTransition = Transition
            transitionDetails.sharedElementReturnTransition = Transition

            returnTransition = Fade()
            transitionDetails.enterTransition = Fade()

            // TODO move it to presenter
            parentFragmentManager.commit {
                addSharedElement(transitionView, getString(R.string.clothingTypeTransition))
                replace(R.id.container, transitionDetails, ClothesSubtypesFragment::class.simpleName)
                addToBackStack(ClothesSubtypesFragment::class.simpleName)
            }

        }

        binding.outerwear.setOnClickListener(clickListener)
        binding.lightClothes.setOnClickListener(clickListener)
        binding.underwear.setOnClickListener(clickListener)
        binding.shoes.setOnClickListener(clickListener)
        binding.accessories.setOnClickListener(clickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}