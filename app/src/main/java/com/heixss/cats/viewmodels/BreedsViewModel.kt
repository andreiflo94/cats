package com.heixss.cats.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heixss.cats.model.local.BreedCard
import com.heixss.cats.model.repositories.BreedsRepository
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedsViewModel @Inject constructor(private val breedsRepository: BreedsRepository) :
    ViewModel() {

    lateinit var unfilteredBreedCards: List<BreedCard>
    val refreshSubject = BehaviorSubject.createDefault(Progress.HIDE)
    val errorSubjectSubject = PublishSubject.create<String>()

    fun loadBreedsAndStore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                changeUIStatus(Progress.SHOW)
                //load breeds
                val response = breedsRepository.loadBreeds()
                if (response.isSuccessful) {
                    //create list of own object type `BreedCard` and store it to local db
                    val breedCards = arrayListOf<BreedCard>()
                    response.body()?.forEach {
                        val breedCard = BreedCard.of(it)
                        breedCards.add(breedCard)
                        //launch jobs for each photo request
                        launch { loadBreedCardImage(breedCard) }
                    } ?: run { showUIError("unexpected error") }
                    //store to local db
                    breedsRepository.storeBreedCards(breedCards)
                    changeUIStatus(Progress.HIDE)
                } else {
                    showUIError("unexpected error")
                }
            } catch (ex: Exception) {
                changeUIStatus(Progress.HIDE)
                ex.printStackTrace()
                ex.message?.let { showUIError(it) } ?: run { showUIError("unexpected error") }
            }
        }
    }

    fun clearCachedBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            breedsRepository.clearBreedCards()
        }
    }

    fun breedCardsLiveData(): LiveData<List<BreedCard>> {
        return breedsRepository.getBreedCards()
    }

    fun isUnfilteredBreedCardsInitialized(): Boolean = ::unfilteredBreedCards.isInitialized

    private suspend fun loadBreedCardImage(breedCard: BreedCard) {
        val pic = breedsRepository.getBreedImage(breedCard.id).body()?.get(0)!!.url
        breedCard.imageUrl = pic
        breedsRepository.updateBreedCard(breedCard)
    }

    private suspend fun changeUIStatus(progress: Progress) {
        withContext(Dispatchers.Main) {
            refreshSubject.onNext(progress)
        }
    }

    private suspend fun showUIError(errorMsj: String) {
        withContext(Dispatchers.Main) {
            errorSubjectSubject.onNext(errorMsj)
        }
    }
}

enum class Progress {
    SHOW,
    HIDE
}
