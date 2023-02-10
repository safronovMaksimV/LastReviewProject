package com.lrp.data.sources

import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.utils.ResultCustomFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DogsRemoteDataSource {
    suspend fun getRandomDogs(): Flow<ResultCustomFlow<ImageResponse?>>

    suspend fun getRandomDogsByBreed(breed: String): Flow<ResultCustomFlow<ImageResponse?>>
    suspend fun searchDogByBreed(breed: String, resultsNumber: Int): Flow<ResultCustomFlow<ImageResponse?>>

    suspend fun getAllBreeds(): Flow<ResultCustomFlow<BreedResponse?>>
}
