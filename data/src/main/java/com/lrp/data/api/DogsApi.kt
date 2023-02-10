package com.lrp.data.api

import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsApi {
    @GET("breeds/image/random/5")
    suspend fun getRandomDogPhoto(): Response<ImageResponse>

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedResponse>

    @GET("breed/{breed}/images/random/5")
    suspend fun getRandomDogsByBreed(
        @Path(
            "breed",
            encoded = true
        ) breed: String
    ): Response<ImageResponse>

    @GET("breed/{breed}/images/random/{number}")
    suspend fun searchDogByBreed(
        @Path(
            "breed",
            encoded = true
        ) breed: String,
        @Path(
            "number",
            encoded = true
        ) numberOfResults: String,
    ): Response<ImageResponse>


}