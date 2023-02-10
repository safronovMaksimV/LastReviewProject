package com.lrp.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrp.base.modules.IoDispatcher
import com.lrp.domain.usecases.DogsUseCase
import com.lrp.domain.utils.ResultCustomFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel@Inject constructor(
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val dogsUseCase: DogsUseCase,
) : ViewModel() {

    private val mutableHomeUiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val homeUiState = mutableHomeUiState.asStateFlow()

    private val mutableRefreshState = MutableStateFlow(true)
    val refreshState = mutableRefreshState.asStateFlow()

    private var resultsNumber = 15
    private var defaultSearchQuery: String = ""

    fun refresh() {
        fetchSearch(defaultSearchQuery)
    }

    fun changeResultsNumber(newResultsNumber: Int) {
        resultsNumber = newResultsNumber
    }

    private fun fetchSearch(searchQuery: String) {
        viewModelScope.launch(ioDispatcher) {
            defaultSearchQuery = searchQuery
            dogsUseCase.searchDogByBreed(defaultSearchQuery, resultsNumber)
                .map {
                    val resultList = arrayListOf<String>()
                    if (it is ResultCustomFlow.Success) {
                        resultList.addAll(it.data?.message.orEmpty())
                    }
                    resultList
                }
                .collectLatest {
                mutableHomeUiState.emit(SearchUiState.Loaded(it))
                delay(400)
                mutableRefreshState.emit(false)
            }
        }
    }
}