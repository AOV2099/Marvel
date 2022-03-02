package com.aov2099.marvel.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aov2099.marvel.databinding.ItemComicBinding
import com.aov2099.marvel.model.Comic

import com.squareup.picasso.Picasso

class ComicViewHolder( view:View ): RecyclerView.ViewHolder( view ) {

    private val binding = ItemComicBinding.bind(view)

    fun bind( comic : Comic) {

        var imgUrl = "${comic.image.url}.${comic.image.ext}"
        imgUrl = imgUrl.replace("http", "https")

        Picasso.get().load(imgUrl).into(binding.ivComicCover)

        binding.tvComicTitle.text = comic.title
        binding.tvComicDesc.text = comic.description


    }

}