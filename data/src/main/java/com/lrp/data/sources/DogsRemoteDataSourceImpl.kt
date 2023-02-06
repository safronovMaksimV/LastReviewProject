package com.lrp.data.sources

import com.lrp.data.api.DogsApi
import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.utils.ResultCustomFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@Singleton
class DogsRemoteDataSourceImpl @Inject constructor(
    private val dogsApi: DogsApi
) : DogsRemoteDataSource {

    override suspend fun getRandomDogs() =
        ResultCustomFlow.suspended {
            dogsApi.getRandomDogPhoto()
        }.flowOn(Dispatchers.IO)

}
