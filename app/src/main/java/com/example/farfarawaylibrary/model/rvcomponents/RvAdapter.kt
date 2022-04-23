package com.example.farfarawaylibrary.model.rvcomponents

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.farfarawaylibrary.model.models.SwSimpleCharacter

class RvAdapter(itemClick: RvItemClick) : ListAdapter<SwSimpleCharacter,RvHolder>(SwComparator()) {

    private val _itemClick = itemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        return RvHolder.create(parent, _itemClick)
    }

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SwComparator : DiffUtil.ItemCallback<SwSimpleCharacter>() {
        override fun areItemsTheSame(oldItem: SwSimpleCharacter, newItem: SwSimpleCharacter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SwSimpleCharacter, newItem: SwSimpleCharacter): Boolean {
            return oldItem.name == newItem.name
        }
    }
}