package com.lrp.data.repositories

import com.lrp.data.sources.DogsRemoteDataSource
import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.repositories.DogsRepository
import com.lrp.domain.utils.ResultCustomFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

@Singleton
class DogsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DogsRemoteDataSource
) : DogsRepository {
    override suspend fun getRandomDog(): Flow<ResultCustomFlow<ImageResponse?>> {
        return remoteDataSource.getRandomDogs()
    }

    override suspend fun getRandomDogByBreed(breed: String): Flow<ResultCustomFlow<ImageResponse?>> {
        return remoteDataSource.getRandomDogsByBreed(breed)
    }

    override suspend fun searchDogByBreed(breed: String, resultsNumber: Int): Flow<ResultCustomFlow<ImageResponse?>> {
        return remoteDataSource.searchDogByBreed(breed, resultsNumber)
    }

    override suspend fun getAllBreeds(): Flow<ResultCustomFlow<BreedResponse?>> {
        return remoteDataSource.getAllBreeds()
    }
}
