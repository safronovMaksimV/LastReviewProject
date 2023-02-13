package com.lrp.search.ui

sealed class SearchUiState {
    object Loading : SearchUiState()

    object Empty : SearchUiState()

    data class Loaded constructor(
        val items: List<String>,
    ) : SearchUiState()
}
