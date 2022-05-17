package com.example.farfarawaylibrary.model.rvcomponents

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.models.SwSimpleCharacter

class CharacterPagingDataAdapter(clickI : RvItemClick) : PagingDataAdapter<SwCompleteCharacter,RvHolder>(diffCallback = SW_COMPARATOR) {

    private val clickInterface = clickI

    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        holder.bind(simpleCharTransform(getItem(position),position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        return RvHolder.create(parent, clickInterface)
    }

    private fun simpleCharTransform(character: SwCompleteCharacter?,position : Int): SwSimpleCharacter? {
        return if (character != null) SwSimpleCharacter(position+1,character.name,character.birth_year)
        else null
    }
}

private val SW_COMPARATOR = object : DiffUtil.ItemCallback<SwCompleteCharacter>() {
    override fun areItemsTheSame(oldItem: SwCompleteCharacter, newItem: SwCompleteCharacter): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: SwCompleteCharacter, newItem: SwCompleteCharacter): Boolean =
        oldItem == newItem
}