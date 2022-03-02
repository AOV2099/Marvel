package com.aov2099.marvel.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aov2099.marvel.databinding.ItemCharacterBinding
import com.aov2099.marvel.model.CharacterClickListener
import com.aov2099.marvel.model.SuperHero
import com.squareup.picasso.Picasso

class CharacterViewHolder (
    view : View,
    private val clickListener: CharacterClickListener

) : RecyclerView.ViewHolder( view ){

    private val binding = ItemCharacterBinding.bind(view)

    fun bind( character : SuperHero) {

        var imgUrl = "${character.image.url}.${character.image.ext}"
        imgUrl = imgUrl.replace("http", "https")

       Picasso.get().load(imgUrl).into(binding.ivCharacter)
        binding.tvCharacterName.text = character.name

        if (character.description != ""){
            binding.tvCharacterDesc.text = character.description

        }else{
            binding.tvCharacterDesc.text = "Sin descripción disponible"
        }

        binding.cvCharacter.setOnClickListener {
            clickListener.onClick( character )
        }
    }


}