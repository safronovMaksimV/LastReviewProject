package com.lrp.domain.usecases

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    @Reusable
    fun providesDogsUseCase(dogsUseCase: DogsUseCaseImpl): DogsUseCase = dogsUseCase

}
