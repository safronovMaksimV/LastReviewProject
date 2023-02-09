package com.lrp.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrp.base.modules.IoDispatcher
import com.lrp.domain.usecases.DogsUseCase
import com.lrp.domain.utils.ResultCustomFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Random
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val dogsUseCase: DogsUseCase,
) : ViewModel() {

    private val mutableHomeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState = mutableHomeUiState.asStateFlow()

    private val mutableRefreshState = MutableStateFlow(true)
    val refreshState = mutableRefreshState.asStateFlow()

    fun refresh() {
        fetchRandomDogs()
    }

    private fun fetchRandomDogs() {
        viewModelScope.launch(ioDispatcher) {
            // get all breeds
            // choose breed randomly
            // get random 5 photos of dogs
            // search for every breed random 5 photos

            dogsUseCase.getAllBreeds().mapLatest {
                var resultBreed = "akita"
                if (it is ResultCustomFlow.Success) {
                    val breeds = it.data?.message.orEmpty()
                    val subBreed = breeds.entries.elementAt(Random().nextInt(breeds.size))
                    resultBreed = if (subBreed.value.isEmpty()) {
                        subBreed.key
                    } else {
                        val breed = subBreed.value.elementAt(Random().nextInt(subBreed.value.size))
                        "${subBreed.key}/$breed"
                    }
                }
                resultBreed
            }.flatMapConcat {
                dogsUseCase.getRandomDogByBreed(it)
            }.combine(dogsUseCase.getRandomDog()) { dogsByBreed, randomDogs ->
                val resultList = arrayListOf<String>()
                if (dogsByBreed is ResultCustomFlow.Success) {
                    resultList.addAll(dogsByBreed.data?.message.orEmpty())
                }
                if (randomDogs is ResultCustomFlow.Success) {
                    resultList.addAll(randomDogs.data?.message.orEmpty())
                }
                resultList
            }.collectLatest {
                mutableHomeUiState.emit(HomeUiState.Loaded(it))
                delay(400)
                mutableRefreshState.emit(false)
            }
        }
    }

    init {
        fetchRandomDogs()
    }
}