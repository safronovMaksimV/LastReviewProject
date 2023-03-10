@file:OptIn(ExperimentalMaterialApi::class)

package com.lrp.home.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lrp.base.composable.PhotoView
import com.lrp.home.ui.HomeUiState
import com.lrp.home.ui.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeView(viewModel: HomeViewModel) {
    val uiState by viewModel.homeUiState.collectAsState()
    val state = rememberLazyListState()
    val isRefreshing by viewModel.refreshState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.refresh() })

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = {
                LazyItemsView(state, uiState, pullRefreshState, isRefreshing)
            }
        )
    }
}

@Composable
fun LazyItemsView(
    state: LazyListState,
    uiState: HomeUiState,
    pullRefreshState: PullRefreshState,
    isRefreshing: Boolean
) {
    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = state,
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is HomeUiState.Loaded -> {
                    items(
                        items = uiState.items,
                        itemContent = {
                            PhotoView(it)
                        }
                    )
                }
                else -> {

                }
            }
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}