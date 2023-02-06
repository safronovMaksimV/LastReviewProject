package com.lrp.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrp.base.modules.IoDispatcher
import com.lrp.domain.usecases.DogsUseCase
import com.lrp.domain.utils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val dogsUseCase: DogsUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch(ioDispatcher) {

            dogsUseCase.getRandomDog().collect {
                Timber.e("SOME PHOTO: $it")
                it.map { response ->
                    Timber.e("BODY: ${response.body()}")
                }
            }
        }
    }
}