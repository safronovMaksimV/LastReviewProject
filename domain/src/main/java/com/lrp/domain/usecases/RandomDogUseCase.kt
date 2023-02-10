package com.lrp.domain.usecases

import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.repositories.DogsRepository
import com.lrp.domain.utils.ResultCustomFlow
import com.lrp.domain.utils.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Response

interface DogsUseCase {
    suspend fun getRandomDog(): Flow<ResultCustomFlow<ImageResponse?>>

    suspend fun getRandomDogByBreed(breed: String): Flow<ResultCustomFlow<ImageResponse?>>
    suspend fun searchDogByBreed(breed: String, resultsNumber: Int): Flow<ResultCustomFlow<ImageResponse?>>

    suspend fun getAllBreeds(): Flow<ResultCustomFlow<BreedResponse?>>
}

@Singleton
class DogsUseCaseImpl @Inject constructor(
    private val dogsRepository: DogsRepository
) : DogsUseCase {
    override suspend fun getRandomDog(): Flow<ResultCustomFlow<ImageResponse?>> {
        return dogsRepository.getRandomDog()
    }

    override suspend fun getRandomDogByBreed(breed: String): Flow<ResultCustomFlow<ImageResponse?>> {
        return dogsRepository.getRandomDogByBreed(breed)
    }

    override suspend fun searchDogByBreed(breed: String, resultsNumber: Int): Flow<ResultCustomFlow<ImageResponse?>> {
        return dogsRepository.searchDogByBreed(breed, resultsNumber)
    }

    override suspend fun getAllBreeds(): Flow<ResultCustomFlow<BreedResponse?>> {
        return dogsRepository.getAllBreeds()
    }
}
