package com.lrp.data.sources

import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.utils.ResultCustomFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DogsRemoteDataSource {
    suspend fun getRandomDogs(): Flow<ResultCustomFlow<Response<ImageResponse>>>
}
