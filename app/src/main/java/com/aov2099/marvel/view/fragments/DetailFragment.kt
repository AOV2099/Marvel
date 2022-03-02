package com.aov2099.marvel.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aov2099.marvel.R
import com.aov2099.marvel.databinding.FragmentDetailBinding
import com.aov2099.marvel.model.CHARACTER_EXTRA_DESC
import com.aov2099.marvel.model.CHARACTER_EXTRA_ID
import com.aov2099.marvel.model.CHARACTER_EXTRA_IMAGE
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val characterDesc = activity?.intent?.extras?.getString(CHARACTER_EXTRA_DESC)
        var imgUrl = activity?.intent?.extras?.getString(CHARACTER_EXTRA_IMAGE)

        imgUrl =  "$imgUrl.jpg"
        imgUrl.replace("http", "https")

        if(!characterDesc.isNullOrEmpty()){
            binding.tvDetailDesc.text = characterDesc
        }else{
            binding.tvDetailDesc.text = "Sin descripción disponible"
        }
        Picasso.get().load(imgUrl).into(binding.ivDetailImage)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        //activity?.intent?.extras?.getString(CHARACTER_EXTRA_ID)

        binding.btnDetailComics.setOnClickListener {

            navController.navigate(R.id.comicsFragment)
        }

        binding.btnDetailUrl.setOnClickListener {
            val bundle = bundleOf(
                Pair( "ID",  activity?.intent?.extras?.getString(CHARACTER_EXTRA_ID) )
            )

            navController.navigate(R.id.urlsFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}