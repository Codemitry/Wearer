package com.codemitry.wearer.myclothes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.R
import com.codemitry.wearer.models.ClothingItem

class MyClothesItemSwipedAdapter(
        private var clothes: List<ClothingItem>,
        private val onItemClickListener: (clothes: ClothingItem) -> Unit
) : RecyclerView.Adapter<MyClothesItemSwipedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: ImageView? = null
        var name: TextView? = null
        var foregroundView: View? = null

        init {
            icon = itemView.findViewById(R.id.clothesTypeIcon)
            name = itemView.findViewById(R.id.clothingItemName)
            foregroundView = itemView.findViewById(R.id.view_foreground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.clothing_item_swiped, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = clothes[position].name
//        holder.icon?.setImageResource(clothes[position].iconResource)

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(clothes[position])
        }
    }

    override fun getItemCount(): Int {
        return clothes.size
    }

}