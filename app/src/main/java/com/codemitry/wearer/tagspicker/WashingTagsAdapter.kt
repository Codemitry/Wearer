package com.codemitry.wearer.tagspicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.R
import com.codemitry.wearer.models.CaringLabels

class WashingTagsAdapter(
        imCaringLabels: List<CaringLabels>,
        private val onRemoveCaringLabelClick: (label: CaringLabels) -> Unit,
) : RecyclerView.Adapter<WashingTagsAdapter.ViewHolder>() {

    private val caringLabels: MutableList<CaringLabels> = imCaringLabels.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tag_image_removable, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(caringLabels[position].iconResource)
        TooltipCompat.setTooltipText(holder.itemView, holder.image.context.getString(caringLabels[position].nameResource))
        holder.removeButton.setOnClickListener { onRemoveCaringLabelClick(caringLabels[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return caringLabels.size
    }

    fun removeLabel(caringLabel: CaringLabels) {
        val removeIdx = caringLabels.indexOf(caringLabel)
        caringLabels.removeAt(removeIdx)
        notifyItemRemoved(removeIdx)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var removeButton: ImageButton = itemView.findViewById(R.id.remove)
    }
}