package com.heixss.cats.model.repositories

import androidx.lifecycle.LiveData
import com.heixss.cats.db.AppDatabase
import com.heixss.cats.model.local.BreedCard
import com.heixss.cats.model.remote.Breed
import com.heixss.cats.model.remote.BreedImage
import com.heixss.cats.network.CatsApi
import retrofit2.Response
import javax.inject.Inject

class BreedsRepository @Inject constructor(
    private val catsApi: CatsApi,
    private val appDatabase: AppDatabase
) {

    fun getBreedCards(): LiveData<List<BreedCard>> {
        return appDatabase.breedCardDao().getBreedCards()
    }

    suspend fun loadBreeds(): Response<List<Breed>> {
        return catsApi.getBreeds()
    }

    suspend fun getBreedImage(breedId: String): Response<List<BreedImage>> {
        return catsApi.getBreedImage(breedId)
    }

    suspend fun storeBreedCards(list: List<BreedCard>) {
        appDatabase.breedCardDao().insert(*list.toTypedArray())
    }

    suspend fun updateBreedCard(breedCard: BreedCard) {
        appDatabase.breedCardDao().update(breedCard)
    }

    fun clearBreedCards() {
        appDatabase.breedCardDao().clearBreedCards()
    }
}