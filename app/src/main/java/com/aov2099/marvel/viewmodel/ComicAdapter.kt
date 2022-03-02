package com.aov2099.marvel.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aov2099.marvel.R
import com.aov2099.marvel.model.Comic

class ComicAdapter (private val comics : List<Comic> ) : RecyclerView.Adapter<ComicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val layoutInflater = LayoutInflater.from( parent.context )
        return ComicViewHolder( layoutInflater.inflate( R.layout.item_comic, parent, false ) )
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val item = comics[position]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return comics.size
    }


}