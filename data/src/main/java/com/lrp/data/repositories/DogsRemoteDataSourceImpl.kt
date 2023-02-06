package com.lrp.data.repositories

import api.DogsApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.ResultCustomFlow

@Singleton
class DogsRemoteDataSourceImpl @Inject constructor(
    private val dogsApi: DogsApi
) : DogsRemoteDataSource {

    override suspend fun getDogs(ownerName: String) =
        withContext(Dispatchers.IO) {
            try {
                val response = dogsApi.getDogs(ownerName)
                if (response.isSuccessful) {
                    return@withContext ResultCustomFlow.Success(response.body()!!)
                } else {
                    return@withContext ResultCustomFlow.Failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext ResultCustomFlow.Failure(e)
            }
        }

/*     fun getDogs2(ownerName: String): kotlinx.coroutines.flow.Flow<Unit> =
         flow {
             emit(dogsApi.getDogs(""))
         }
             .flowOn(Dispatchers.IO)*/
}
