package com.lrp.data.modules

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [CoreNetworkModule::class])
abstract class DataModule {

/*    @Binds
    abstract fun bindsDogsRepository(repository: DogsRepositoryImpl): DogsRepository

    @Binds
    abstract fun bindsDogsLocalDataSource(
        localDataSource: DogsLocalDataSourceImpl
    ): DogsLocalDataSource

    @Binds
    abstract fun bindsDogsRemoteDataSource(
        remoteDataSource: DogsRemoteDataSourceImpl
    ): DogsRemoteDataSource

    @Binds
    abstract fun bindsOwnersRepository(repository: OwnersRepositoryImpl): OwnersRepository

    @Binds
    abstract fun bindsOwnersLocalDataSource(
        localDataSource: OwnersLocalDataSourceImpl
    ): OwnersLocalDataSource

    @Binds
    abstract fun bindsOwnersRemoteDataSource(
        remoteDataSource: OwnersRemoteDataSourceImpl
    ): OwnersRemoteDataSource*/
}
