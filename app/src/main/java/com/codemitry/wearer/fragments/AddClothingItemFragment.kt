package com.codemitry.wearer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codemitry.wearer.databinding.FragmentAddClothingItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AddClothingItemFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddClothingItemBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddClothingItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelAddingClothingItem.setOnClickListener {
            dismiss()
        }

        binding.purchaseYear.setStartIconOnClickListener {
            showYearPicker()
        }
    }

    private fun showYearPicker() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val picker = NumberPickerDialog(currentYear - 50, currentYear) { pickedYear ->
            binding.purchaseYear.editText?.setText(pickedYear.toString())
        }
        picker.show(childFragmentManager, NumberPickerDialog::class.simpleName)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}