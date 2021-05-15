package com.codemitry.wearer.clothingitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.R
import com.codemitry.wearer.models.CaringLabels

class CaringLabelsAdapter(
    private val caringLabels: List<CaringLabels>
) : RecyclerView.Adapter<CaringLabelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.caring_label_image, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(caringLabels[holder.adapterPosition].iconResource)
        TooltipCompat.setTooltipText(
            holder.itemView,
            holder.image.context.getString(caringLabels[holder.adapterPosition].nameResource)
        )
    }

    override fun getItemCount(): Int {
        return caringLabels.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
    }
}