package com.codemitry.wearer.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codemitry.wearer.ComponentsProvider
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentClothesTypesMenuBinding
import com.codemitry.wearer.models.ClothesTypesByWearingWay


class ClothesTypesMenuFragment : Fragment() {

    private var _binding: FragmentClothesTypesMenuBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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

        val clickListener = { view: View ->
            val transitionView: View
            val clothesType = when (view.id) {
                R.id.outerwear -> {
                    binding.outerwear.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.outerwear

                    ClothesTypesByWearingWay.OUTERWEAR  // return value
                }
                R.id.lightClothes -> {
                    binding.lightClothes.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.lightClothes
                    ClothesTypesByWearingWay.LIGHT_CLOTHES  // return value
                }
                R.id.underwear -> {
                    binding.underwear.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.underwear
                    ClothesTypesByWearingWay.UNDERWEAR  // return value
                }
                R.id.shoes -> {
                    binding.shoes.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.shoes
                    ClothesTypesByWearingWay.SHOES  // return value
                }
                R.id.accessories -> {
                    binding.accessories.transitionName = ClothesSubtypesActivity.SHARED_IMAGE_NAME
                    transitionView = binding.accessories
                    ClothesTypesByWearingWay.ACCESSORIES  // return value
                }
                else -> error("Unexpected clothes type")
            }
            (requireActivity().application as ComponentsProvider).clothesType = clothesType
            ClothesSubtypesActivity.start(requireActivity(), clothesType, transitionView)
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