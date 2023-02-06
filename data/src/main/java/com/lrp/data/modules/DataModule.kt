package com.lrp.data.modules

import com.lrp.data.repositories.DogsRepositoryImpl
import com.lrp.data.sources.DogsRemoteDataSource
import com.lrp.data.sources.DogsRemoteDataSourceImpl
import com.lrp.domain.repositories.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [CoreNetworkModule::class])
abstract class DataModule {

    @Binds
    abstract fun bindsDogsRepository(repository: DogsRepositoryImpl): DogsRepository

    @Binds
    abstract fun bindsDogsRemoteDataSource(
        remoteDataSource: DogsRemoteDataSourceImpl
    ): DogsRemoteDataSource

}
