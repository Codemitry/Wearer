package com.codemitry.wearer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentWashingTagsPickerBinding
import com.codemitry.wearer.models.CaringLabels
import com.codemitry.wearer.tagspicker.CheckableLabelsAdapter

class CaringLabelsPickerFragment(
        private val caringLabels: List<CaringLabels>,
        private val selectedCaringLabels: List<CaringLabels>? = null,
        private val onCaringLabelsSelected: (caringLabels: List<CaringLabels>) -> Unit
) : DialogFragment() {

    private var _binding: FragmentWashingTagsPickerBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWashingTagsPickerBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tagList.adapter = CheckableLabelsAdapter(caringLabels, selectedCaringLabels)

        binding.confirm.setOnClickListener {
            onCaringLabelsSelected((binding.tagList.adapter as CheckableLabelsAdapter).getCheckedLabels())
            dialog?.dismiss()
        }
        binding.cancel.setOnClickListener { dialog?.dismiss() }
    }

}