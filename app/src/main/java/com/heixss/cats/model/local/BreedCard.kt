package com.heixss.cats.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.heixss.cats.model.remote.Breed
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class BreedCard(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    var imageUrl: String?,
    val countryCode: String,
    val temperament: String,
    val wikiLink: String
): Parcelable {
    companion object{
        fun of(breed: Breed): BreedCard{
            return BreedCard(
                breed.id,
                breed.name,
                breed.description,
                null,
                breed.countryCode,
                breed.temperament,
                breed.wikipediaUrl
            )
        }
    }
}