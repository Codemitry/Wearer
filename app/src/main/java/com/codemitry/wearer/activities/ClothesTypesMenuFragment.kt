package com.codemitry.wearer.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentClothesTypesMenuBinding
import com.codemitry.wearer.models.ClothesTypesByWearingWay


class ClothesTypesMenuFragment : Fragment(), View.OnClickListener {

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


        binding.outerwear.setOnClickListener(this)
        binding.lightClothes.setOnClickListener(this)
        binding.underwear.setOnClickListener(this)
        binding.shoes.setOnClickListener(this)
        binding.accessories.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(view: View) {
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

        val sharedTransitionExtras = FragmentNavigatorExtras(transitionView to getString(R.string.clothingTypeTransition))

        val action = ClothesTypesMenuFragmentDirections.actionBottomMenuCloakroomToClothesSubtypesFragment(clothesType)
            requireActivity().findNavController(R.id.nav_host_fragment).navigate(action, sharedTransitionExtras)

    }
}