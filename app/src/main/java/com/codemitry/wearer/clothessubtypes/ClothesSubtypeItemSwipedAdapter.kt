package com.codemitry.wearer.clothessubtypes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.R
import com.codemitry.wearer.models.ClothesSubtype
import com.codemitry.wearer.mvp.contracts.clothessubtypes.ClothesSubtypesContract

class ClothesSubtypeItemSwipedAdapter(
        private var clothesTypes: List<ClothesSubtype>,
        private val onItemClickListener: ClothesSubtypesContract.OnClothesTypeAddClickListenerForFramework
) : RecyclerView.Adapter<ClothesSubtypeItemSwipedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: ImageView? = null
        var name: TextView? = null
        var foregroundView: View? = null

        init {
            icon = itemView.findViewById(R.id.clothesTypeIcon)
            name = itemView.findViewById(R.id.clothesTypeName)
            foregroundView = itemView.findViewById(R.id.view_foreground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.clothes_subtype_item_swiped, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = holder.name?.context?.getText(clothesTypes[position].nameResource)
        holder.icon?.setImageResource(clothesTypes[position].iconResource)

        holder.itemView.setOnClickListener { view ->
            holder.icon!!.transitionName = view.context.getString(R.string.clothingSubtypeTransition)
            onItemClickListener.onAddClothesTypeClick(clothesTypes[position], holder.icon!!)
        }
    }

    override fun getItemCount(): Int {
        return clothesTypes.size
    }

}