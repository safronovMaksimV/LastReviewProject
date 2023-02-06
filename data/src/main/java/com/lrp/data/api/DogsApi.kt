package com.lrp.data.api

import com.lrp.domain.enteties.ImageResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsApi {
    @GET("breeds/image/random/5")
    suspend fun getRandomDogPhoto(): Response<ImageResponse>
}