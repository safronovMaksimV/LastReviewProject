package com.lrp.data.sources

import com.lrp.data.api.DogsApi
import com.lrp.domain.enteties.BreedResponse
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.utils.ResultCustomFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@Singleton
class DogsRemoteDataSourceImpl @Inject constructor(
    private val dogsApi: DogsApi
) : DogsRemoteDataSource {

    override suspend fun getRandomDogs() =
        ResultCustomFlow.suspended {
            val result = dogsApi.getRandomDogPhoto()
            if (result.isSuccessful) {
                result.body()
            } else {
                ImageResponse("", emptyList())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getRandomDogsByBreed(breed: String)=
        ResultCustomFlow.suspended {
            val result = dogsApi.getRandomDogsByBreed(breed)
            if (result.isSuccessful) {
                result.body()
            } else {
                ImageResponse("", emptyList())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun searchDogByBreed(breed: String, resultsNumber: Int)=
        ResultCustomFlow.suspended {
            val result = dogsApi.searchDogByBreed(breed, resultsNumber.toString())
            if (result.isSuccessful) {
                result.body()
            } else {
                ImageResponse("", emptyList())
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getAllBreeds() =
        ResultCustomFlow.suspended {
            val result = dogsApi.getAllBreeds()
            if (result.isSuccessful) {
                result.body()
            } else {
                BreedResponse("", emptyMap())
            }
        }.flowOn(Dispatchers.IO)

}
