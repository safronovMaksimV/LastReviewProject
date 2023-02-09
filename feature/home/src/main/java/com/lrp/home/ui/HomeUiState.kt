package com.lrp.home.ui

import com.lrp.domain.enteties.ImageResponse

sealed class HomeUiState {
    object Loading : HomeUiState()

    object Error : HomeUiState()

    data class Loaded constructor(
        val items: List<String>,
    ) : HomeUiState()
}
