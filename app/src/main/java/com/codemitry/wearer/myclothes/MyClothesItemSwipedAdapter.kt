package com.codemitry.wearer.myclothes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codemitry.wearer.R
import com.codemitry.wearer.models.ClothingItem
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyClothesItemSwipedAdapter(
    clothingItems: List<ClothingItem>,
    private val onItemClickListener: (clothes: ClothingItem) -> Unit
) : RecyclerView.Adapter<MyClothesItemSwipedAdapter.ViewHolder>() {

    private val clothes =
        if (clothingItems.isEmpty()) mutableListOf() else clothingItems.toMutableList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo: ImageView? = itemView.findViewById(R.id.clothingItemPhoto)
        var name: TextView? = itemView.findViewById(R.id.clothingItemName)
        var foregroundView: View? = itemView.findViewById(R.id.view_foreground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.clothing_item_swiped, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = clothes[holder.adapterPosition].name


        clothes[holder.adapterPosition].photoUrl?.let {
            Glide.with(holder.photo!!.context /* context */)
                .load(Firebase.storage.getReferenceFromUrl(it))
                .into(holder.photo!!)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(clothes[holder.adapterPosition])
        }
    }

    fun addItem(clothingItem: ClothingItem, position: Int) {
        clothes.add(position, clothingItem)
        notifyItemInserted(position)
    }

    fun removeItem(clothingItem: ClothingItem) {
        val position = clothes.indexOf(clothingItem)
        clothes.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

}