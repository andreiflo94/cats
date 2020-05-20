package com.heixss.cats.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.heixss.cats.model.local.BreedCard

@Dao
abstract class BreedCardDao : BaseDao<BreedCard> {

    @Query("SELECT * FROM BreedCard ORDER BY name ASC")
    abstract fun getBreedCards(): LiveData<List<BreedCard>>

    @Query("DELETE FROM BreedCard")
    abstract fun clearBreedCards()
}