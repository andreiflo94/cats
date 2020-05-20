package com.heixss.cats.network

import com.heixss.cats.model.remote.Breed
import com.heixss.cats.model.remote.BreedImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {

    @GET("breeds")
    suspend fun getBreeds():
            Response<List<Breed>>

    @GET("images/search")
    suspend fun getBreedImage(@Query("breed_id") breedId: String):
            Response<List<BreedImage>>

}