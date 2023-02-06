package com.lrp.data.modules

import com.lrp.data.api.DogsApi
import com.lrp.data.modules.CoreNetworkModule.provideBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module(includes = [CoreNetworkModule::class])
object ApiModule {

    @Provides
    fun providesDogsApi(
        retrofitBuilder: Retrofit.Builder
    ): DogsApi = retrofitBuilder
        .baseUrl(provideBaseUrl())
        .build()
        .create(DogsApi::class.java)
}
