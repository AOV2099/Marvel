package com.aov2099.marvel.view.activities


import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aov2099.marvel.R

import com.aov2099.marvel.model.CHARACTER_EXTRA_ID
import com.aov2099.marvel.model.CHARACTER_EXTRA_IMAGE
import com.aov2099.marvel.model.CHARACTER_EXTRA_NAME

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val characterName = intent.getStringExtra(CHARACTER_EXTRA_NAME)
        //val characterImg = intent.getStringExtra(CHARACTER_EXTRA_IMAGE)
        //Toast.makeText(this, characterImg, Toast.LENGTH_SHORT).show()
        val actionBar = supportActionBar
        actionBar!!.title = characterName


    }

}
