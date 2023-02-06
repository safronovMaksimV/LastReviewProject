package com.lrp.domain.usecases

import com.lrp.domain.enteties.ImageResponse
import com.lrp.domain.repositories.DogsRepository
import com.lrp.domain.utils.ResultCustomFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface DogsUseCase {
    suspend fun getRandomDog(): Flow<ResultCustomFlow<Response<ImageResponse>>>
}

@Singleton
class DogsUseCaseImpl @Inject constructor(
    private val dogsRepository: DogsRepository
) : DogsUseCase {
    override suspend fun getRandomDog(): Flow<ResultCustomFlow<Response<ImageResponse>>> {
        return dogsRepository.getRandomDog()
    }
}
