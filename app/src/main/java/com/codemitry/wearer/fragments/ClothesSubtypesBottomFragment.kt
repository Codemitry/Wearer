package com.codemitry.wearer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codemitry.wearer.databinding.FragmentClothesSubtypesBinding
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.models.ClothesSubtypeItemAdapter
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ClothesSubtypesBottomFragment(private val onClothesTypeAddClickListener: ClothesSubtypesContract.OnClothesTypeAddClickListener) :
    BottomSheetDialogFragment() {

    private lateinit var listAdapter: ClothesSubtypeItemAdapter

    private var _binding: FragmentClothesSubtypesBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothesSubtypesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clothesSubtypesList.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showClothesTypes(clothes: List<ClothesSubtype>) {
        listAdapter = ClothesSubtypeItemAdapter(clothes, onClothesTypeAddClickListener)
    }

}