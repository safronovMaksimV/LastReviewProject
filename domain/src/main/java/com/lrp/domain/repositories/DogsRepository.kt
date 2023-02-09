package com.lrp.domain.repositories

import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.utils.ResultCustomFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface DogsRepository {

    suspend fun getRandomDog(): Flow<ResultCustomFlow<ImageResponse?>>
    suspend fun getRandomDogByBreed(breed: String): Flow<ResultCustomFlow<ImageResponse?>>

    suspend fun getAllBreeds(): Flow<ResultCustomFlow<BreedResponse?>>

}
