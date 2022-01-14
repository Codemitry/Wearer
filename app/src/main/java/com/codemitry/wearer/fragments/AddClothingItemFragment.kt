package com.codemitry.wearer.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.FragmentAddClothingItemBinding
import com.codemitry.wearer.models.CaringLabels
import com.codemitry.wearer.models.ClothingItem
import com.codemitry.wearer.mvp.contracts.addclothingitem.AddClothingItemContract
import com.codemitry.wearer.tagspicker.WashingTagsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stfalcon.imageviewer.StfalconImageViewer
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject


class AddClothingItemFragment(private val onItemAdded: ((clothingItem: ClothingItem) -> Unit)? = null) :
    BottomSheetDialogFragment(), AddClothingItemContract.View {

    private var _binding: FragmentAddClothingItemBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: AddClothingItemContract.Presenter

    companion object {
        const val TAKE_PHOTO_RC = 141
        const val PICK_PHOTO_RC = 142
    }

    private var clothingItemPhotoPath: String? = null
    private var clothingItemPhoto: Bitmap? = null

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
            presenter.onPickPurchaseYearClick()
        }

        binding.addWashingTag.setOnClickListener {
            presenter.onAddCaringLabelsClick()
        }

        binding.deleteImage.setOnClickListener { presenter.onDeleteImageClick() }

        binding.addPhoto.setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setItems(arrayOf(getString(R.string.takePhoto), getString(R.string.pickFromGallery)).run {
                        if (clothingItemPhoto != null) this.plus(getString(R.string.openSelectedPhoto)) else this }
                    ) { dialog, which ->
                        when (which) {
                            0 -> takePhoto()
                            1 -> choosePhotoFromGallery()
                            2 -> openSelectedPhoto()
                            else -> dismiss()
                        }
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()

        }

        binding.addClothingItem.setOnClickListener { presenter.onAddClothingItemClick() }
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

    private fun bitmapToBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_PHOTO_RC -> {
                if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data ?: return

                    clothingItemPhoto =
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, selectedImage))
                            } else {
                                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImage)
                            }

                    clothingItemPhoto?.let {
                        presenter.onItemClothingPhotoReady(bitmapToBytes(it))
                    }
                    binding.addPhoto.setImageBitmap(clothingItemPhoto)
                }
            }
            TAKE_PHOTO_RC -> {
                if (resultCode == RESULT_OK) {
                    clothingItemPhoto = BitmapFactory.decodeFile(Uri.parse(clothingItemPhotoPath).path, null)

                    clothingItemPhoto?.let {
                        presenter.onItemClothingPhotoReady(bitmapToBytes(it))
                    }

                    binding.addPhoto.setImageBitmap(clothingItemPhoto)
                }
            }
        }
    }

    override fun takePhoto() {
        val imagePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
                ?: return)
        val newFile = File(imagePath, "clothing_item.jpg")

        val contentUri = FileProvider.getUriForFile(context!!, "com.codemitry.wearer.fileprovider", newFile)

        clothingItemPhotoPath = "file:" + newFile.absolutePath
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        startActivityForResult(intent, TAKE_PHOTO_RC)
    }

    override fun choosePhotoFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, PICK_PHOTO_RC)
    }

    override fun openSelectedPhoto() {
        StfalconImageViewer.Builder(requireContext(), listOf(clothingItemPhoto)) { view, image ->
            Glide.with(this)
                .load(image)
                .into(view)
        }.withTransitionFrom(binding.addPhoto).show()
    }

    override fun deleteImage() {
        binding.addPhoto.setImageDrawable(null)
    }

    override fun getItemClothingName(): String? {
        val text = binding.name.editText?.text?.toString()
        if (text?.isEmpty() == true)
            return null
        return text
    }

    override fun getClothingItemBrand(): String? {
        val text = binding.brand.editText?.text?.toString()
        if (text?.isEmpty() == true)
            return null
        return text
    }

    override fun getClothingItemSize(): String? {
        val text = binding.size.editText?.text?.toString()
        if (text?.isEmpty() == true)
            return null
        return text
    }

    override fun getClothingItemPurchaseYear(): Int? {
        val text = binding.purchaseYear.editText?.text?.toString()
        if (text == null || text.isEmpty())
            return null
        return text.toInt()
    }

    override fun getClothingItemMaterial(): String? {
        val text = binding.material.editText?.text?.toString()
        if (text?.isEmpty() == true)
            return null
        return text
    }

    override fun getClothingItemNote(): String? {
        val text = binding.note.editText?.text?.toString()
        if (text?.isEmpty() == true)
            return null
        return text
    }

    override fun showNameIsEmptyError() {
        Toast.makeText(context, R.string.clothingItemNameMustNotBeEmpty, Toast.LENGTH_LONG).show()
    }

    override fun showErrorAddingClothingItem() {
        Toast.makeText(context, "Error on saving clothing item", Toast.LENGTH_LONG).show()

    }


    override fun showCaringLabelsPicker(caringLabels: List<CaringLabels>) {
        CaringLabelsPickerFragment(
                caringLabels,
                onCaringLabelsSelected = presenter::onCaringLabelsPicked
        ).show(childFragmentManager, CaringLabelsPickerFragment::class.simpleName)

    }

    override fun showCaringLabelsPicker(caringLabels: List<CaringLabels>, selectedCaringLabels: List<CaringLabels>) {
        CaringLabelsPickerFragment(
                caringLabels,
                selectedCaringLabels,
                presenter::onCaringLabelsPicked
        ).show(childFragmentManager, CaringLabelsPickerFragment::class.simpleName)
    }

    override fun showPurchaseYearPicker(fromYear: Int, toYear: Int) {

        val picker = NumberPickerDialog(fromYear, toYear) { pickedYear ->
            binding.purchaseYear.editText?.setText(pickedYear.toString())
        }
        picker.titleResource = R.string.pick_year
        picker.show(childFragmentManager, NumberPickerDialog::class.simpleName)
    }

    override fun showPickedCaringLabels(caringLabels: List<CaringLabels>) {
        binding.tagList.adapter = WashingTagsAdapter(caringLabels, presenter::onCaringLabelRemoveClick)
        binding.tagList.adapter?.notifyDataSetChanged()
    }

    override fun showPickedYear(pickedYear: Int) {
        binding.purchaseYear.editText?.setText(pickedYear.toString())
    }

    override fun removeCaringLabel(caringLabel: CaringLabels) {
        (binding.tagList.adapter as WashingTagsAdapter).removeLabel(caringLabel)
    }

    override fun closeFragment(clothingItem: ClothingItem) {
        onItemAdded?.invoke(clothingItem)
        dismiss()
    }
}