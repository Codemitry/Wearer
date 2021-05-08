package com.codemitry.wearer.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentNumberPickerBinding

class NumberPickerDialog(val min: Int, val max: Int, val onYearPicked: (year: Int) -> Unit) : DialogFragment() {

    var titleResource: Int? = null

    private var _binding: FragmentNumberPickerBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentNumberPickerBinding.inflate(LayoutInflater.from(context))
        println("build dialog")
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        titleResource?.let { titleResource -> binding.titleView.text = getString(titleResource) }
        binding.numberPicker.minValue = min
        binding.numberPicker.maxValue = max
        binding.numberPicker.value = max
        binding.cancel.setOnClickListener { dialog.dismiss() }
        binding.ok.setOnClickListener { onYearPicked(binding.numberPicker.value); dialog.dismiss() }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}