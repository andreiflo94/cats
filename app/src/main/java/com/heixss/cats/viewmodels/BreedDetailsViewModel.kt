package com.heixss.cats.viewmodels

import androidx.lifecycle.ViewModel
import com.heixss.cats.model.local.BreedCard
import javax.inject.Inject

class BreedDetailsViewModel @Inject constructor() : ViewModel() {
    lateinit var breedCard: BreedCard
}
