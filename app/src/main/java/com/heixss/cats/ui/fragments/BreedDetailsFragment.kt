package com.heixss.cats.ui.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.heixss.cats.R
import com.heixss.cats.viewmodels.BreedDetailsViewModel
import kotlinx.android.synthetic.main.breed_details_fragment.*

class BreedDetailsFragment : BaseFragment(R.layout.breed_details_fragment) {

    private val viewModel: BreedDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.breedCard = BreedDetailsFragmentArgs.fromBundle(it).breedCard
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUI()
    }

    private fun configUI() {
        viewModel.breedCard.imageUrl?.let {
            Glide.with(requireActivity()).load(it)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(iv_breed_pic)
        }
        tv_breed.text = viewModel.breedCard.name
        tv_breed_desc.text = viewModel.breedCard.description
        tv_breed_country_code.text = viewModel.breedCard.countryCode
        tv_breed_temperament.text = viewModel.breedCard.temperament
        tv_breed_wiki_link.movementMethod = LinkMovementMethod.getInstance()
        tv_breed_wiki_link.text = viewModel.breedCard.wikiLink
    }
}
