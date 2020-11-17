package com.heixss.cats.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.heixss.cats.model.local.BreedCard

class BreedDetailsViewModel @ViewModelInject constructor() : ViewModel() {

    lateinit var breedCard: BreedCard
}
