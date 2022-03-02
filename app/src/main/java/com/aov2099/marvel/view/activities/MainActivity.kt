package com.aov2099.marvel.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aov2099.marvel.R
import com.aov2099.marvel.databinding.ActivityMainBinding
import com.aov2099.marvel.model.*
import com.aov2099.marvel.utils.Network
import com.aov2099.marvel.viewmodel.CharacterAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, CharacterClickListener {

    //variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharacterAdapter
    private val characterData = mutableListOf<SuperHero>()

    private var searchHadResults = false

    private var isRVInfinite = true


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        super.onCreate(savedInstanceState)

        //View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.svCharacters.setOnQueryTextListener(this)
        setContentView(binding.root)

        saveSessionData()
        setUp()
        setUpRv()

        if(Network.conExistsAct(this)){
            loadCharacters()
        }else{
            Toast.makeText(this, "Check Internet connection", Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveSessionData(){

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        //Guardado de datos.
        val prefs = getSharedPreferences( getString(R.string.prefs_data), Context.MODE_PRIVATE ).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }



    private fun setUp(){

        binding.fabLogOut.setOnClickListener {
            logOut()
        }

    }

    private fun logOut(){

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("LogOut")
        dialog.setMessage("Are you sure you want to Log Out")
        dialog.setPositiveButton("No"){ view, _ -> view.dismiss()}
        dialog.setNegativeButton("Yes"){ view, _->

            val prefs = getSharedPreferences( getString(R.string.prefs_data), Context.MODE_PRIVATE ).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            finish()

        }
        dialog.create()
        dialog.show()


    }


    private fun setUpRv() {

        adapter = CharacterAdapter(characterData, this)
        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
        binding.rvCharacters.adapter = adapter

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.rvCharacters.canScrollVertically(1) && isRVInfinite) {
                    //Toast.makeText(this@MainActivity, "Finsih", Toast.LENGTH_SHORT).show()
                    loadCharacters()
                }
            }
        })


    }



    //Query Listeners
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onQueryTextSubmit(query: String?): Boolean {

        if (!query.isNullOrBlank()) {
            if(Network.conExistsAct(this)){
                searchCharacterByName(query.lowercase(Locale.getDefault()))
            }else{
                Toast.makeText(this, "Check Internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        return false

    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query.equals("")) {
            loadCharacters()
        }

        return false
    }



    //Creates our retrofit Instance
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun loadCharacters() {

       /* if (binding.rvCharacters.childCount >= 1){//clean rv
            characterData.clear()
        }*/

        CoroutineScope(Dispatchers.IO).launch {

            val retrofitCall = getRetrofit()
                .create(ApiServiceMarvel::class.java)
                .getCharacters("characters?apikey=$API_KEY&hash=$API_HASH&ts=$API_TIMESTAMP&limit=$API_LIMIT")

            // Gets to SuperHero objects
            val res: List<SuperHero>? = retrofitCall.body()?.responseData?.characters

            //Affects ui
            runOnUiThread {
                if (retrofitCall.isSuccessful) {
                    val characters: List<SuperHero> = res ?: emptyList()


                    if (characters.isNotEmpty()) {

                        if (searchHadResults){
                            characterData.clear()
                        }

                        characterData.addAll(characters)
                        adapter.notifyDataSetChanged()


                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error al cargar los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }

        isRVInfinite = true
        binding.pbCharacters.visibility = View.VISIBLE

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun searchCharacterByName(characterName: String) {


        CoroutineScope(Dispatchers.IO).launch {

            val retrofitCall = getRetrofit().create(ApiServiceMarvel::class.java)
                .getCharacters("characters?apikey=$API_KEY&hash=$API_HASH&ts=$API_TIMESTAMP&limit=$API_LIMIT&name=$characterName")

            // Gets to SuperHero objects
            val res: List<SuperHero>? = retrofitCall.body()?.responseData?.characters


            //Affects ui
            runOnUiThread {
                if (retrofitCall.isSuccessful) {
                    val characters: List<SuperHero> = res ?: emptyList()


                    if (characters.isNotEmpty()) {
                        characterData.clear()
                        characterData.addAll(characters)
                        adapter.notifyDataSetChanged()
                        searchHadResults = true

                    } else {
                        searchHadResults = false
                        Toast.makeText(
                            this@MainActivity,
                            "Sin coindicencias para la busqueda",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }
        }

        isRVInfinite = false
        binding.pbCharacters.visibility = View.INVISIBLE
    }

    override fun onBackPressed() { // does nothing
       // super.onBackPressed()
    }

    override fun onClick(character: SuperHero) {

        val intent = Intent( applicationContext, CharacterDetailActivity::class.java )

        intent.putExtra(CHARACTER_EXTRA_ID, character.id.toString())
        intent.putExtra(CHARACTER_EXTRA_NAME, character.name)
        intent.putExtra(CHARACTER_EXTRA_DESC, character.description)
        intent.putExtra(CHARACTER_EXTRA_IMAGE, character.image.url)

        startActivity(intent)
    }
}