package com.codemitry.wearer.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentYesNoDialogBinding

class YesNoDialogFragment(val onConfirm: () -> Unit, val onDismiss: () -> Unit) : DialogFragment() {

    private var _binding: FragmentYesNoDialogBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    var title: String? = null
    var body: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentYesNoDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

        binding.positiveButton.setOnClickListener { dismiss(); onConfirm() }
        binding.negativeButton.setOnClickListener { dismiss(); onDismiss() }
        binding.title.text = title
        binding.body.text = body

        return dialog
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_shape)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}