package com.codemitry.wearer.tagspicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.R
import com.codemitry.wearer.models.CaringLabels

class CheckableLabelsAdapter(
        private val caringLabels: List<CaringLabels>,
        selectedCaringLabels: List<CaringLabels>? = null,
) : RecyclerView.Adapter<CheckableLabelsAdapter.ViewHolder>() {

    private val checkedPositionsList: MutableList<Int> = selectedCaringLabels?.map { caringLabels.indexOf(it) }?.toMutableList()
            ?: mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.checkable_tag, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(caringLabels[position].iconResource)
        TooltipCompat.setTooltipText(holder.itemView, holder.image.context.getString(caringLabels[position].nameResource))

        holder.checker.isChecked = checkedPositionsList.contains(position)

        holder.checker.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // limit checked labels count
                if (checkedPositionsList.size < 15)
                    checkedPositionsList.add(position)
            } else {
                checkedPositionsList.remove(position)
            }
        }
    }

    fun getCheckedLabels(): List<CaringLabels> {
        val selectedLabels = mutableListOf<CaringLabels>()

        for (position in checkedPositionsList.sorted()) {
            selectedLabels.add(caringLabels[position])
        }
        return selectedLabels
    }

    override fun getItemCount(): Int {
        return caringLabels.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var checker: ToggleButton = itemView.findViewById(R.id.checker)
    }
}