package com.example.farfarawaylibrary.model.rvcomponents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.model.models.SwSimpleCharacter

class RvHolder (itemView : View, private val itemClick: RvItemClick) : RecyclerView.ViewHolder(itemView) {
    private val id = itemView.findViewById<TextView>(R.id.rv_characterNumber)
    private val name = itemView.findViewById<TextView>(R.id.rv_characterName)
    private val birthDay = itemView.findViewById<TextView>(R.id.rv_characterBirthDay)

    fun bind(swCharacter : SwSimpleCharacter?){
        if (swCharacter!=null){
            id.text = swCharacter.id.toString()
            name.text = swCharacter.name
            birthDay.text = swCharacter.birth_year

            itemView.setOnClickListener{
                if (absoluteAdapterPosition != ListView.INVALID_POSITION){
                    itemClick.onItemClick(name.text.toString())
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, itemClick: RvItemClick): RvHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_character_list_item, parent, false)
            return RvHolder(view, itemClick)
        }
    }
}