package com.aov2099.marvel.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aov2099.marvel.R
import com.aov2099.marvel.model.CharacterClickListener
import com.aov2099.marvel.model.SuperHero

class CharacterAdapter(
    private val characters : List<SuperHero> ,
    private val clickListener: CharacterClickListener
    ) : RecyclerView.Adapter<CharacterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )
        return CharacterViewHolder( layoutInflater.inflate( R.layout.item_character, parent, false ),  clickListener )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = characters[position]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return characters.size
    }


}