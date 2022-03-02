package com.aov2099.marvel.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.aov2099.marvel.databinding.FragmentComicsBinding
import com.aov2099.marvel.model.*
import com.aov2099.marvel.viewmodel.ComicAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicsFragment : Fragment() {

    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ComicAdapter
    private val comicData = mutableListOf<Comic>()
    //private val grid = GridLayoutManager(requireContext(), 2)


    private lateinit var characterId : String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentComicsBinding.inflate(inflater, container, false)

        val characterDesc = activity?.intent?.extras?.getString(CHARACTER_EXTRA_DESC).toString()
        characterId = activity?.intent?.extras?.getString(CHARACTER_EXTRA_ID).toString()

        Log.d( "---------->", characterDesc )
        Log.d( "ID ---------->", characterId )

        setUpRv()
        getComicsByCharacterId( )

        return binding.root

    }


    private fun setUpRv() {

        adapter = ComicAdapter(comicData)
        binding.rvComics.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvComics.adapter = adapter

    }


    //Creates our retrofit Instance
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/characters/${characterId}/" )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getComicsByCharacterId() {

        CoroutineScope(Dispatchers.IO).launch {

            val retrofitCall = getRetrofit()
                .create(ApiServiceMarvel::class.java)
                .getComisByCharacter("comics?apikey=$API_KEY&hash=$API_HASH&ts=$API_TIMESTAMP")

            // Gets to Comic objects
            val res: List<Comic>? = retrofitCall.body()?.responseData?.comics

            //Affects ui
            activity?.runOnUiThread {


                if (retrofitCall.isSuccessful) {
                    val comics: List<Comic> = res ?: emptyList()

                    if ( comics.isNotEmpty() ){

                        comicData.clear()
                        comicData.addAll(comics)
                        adapter.notifyDataSetChanged()


                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al cargar comics",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            }
        }

    }

}