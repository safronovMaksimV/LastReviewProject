package com.lrp.search.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lrp.base.composable.PhotoView
import com.lrp.search.R
import com.lrp.search.ui.SearchUiState
import com.lrp.search.ui.SearchViewModel

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchView(viewModel: SearchViewModel) {
    val uiState by viewModel.homeUiState.collectAsState()
    val state = rememberLazyListState()
    val isRefreshing by viewModel.refreshState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.refresh() })

    MaterialTheme {
        Scaffold(
            topBar = { SearchAppBar(viewModel) },
            modifier = Modifier.fillMaxSize(),
            content = {
                LazyItemsView(state, uiState, pullRefreshState, isRefreshing)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyItemsView(
    state: LazyListState,
    uiState: SearchUiState,
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
                is SearchUiState.Loaded -> {
                    items(
                        items = uiState.items,
                        itemContent = {
                            PhotoView(it)
                        }
                    )
                }
                else -> {
                    item {
                        Text(
                            text = "There is no results",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
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

@Composable
private fun SearchAppBar(
    viewModel: SearchViewModel,
) {
    // Immediately update and keep track of query from text field changes.
    var query: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            // If user makes changes to text, immediately updated it.
            query = onQueryChanged
            // To avoid crash, only query when string isn't empty.
            if (onQueryChanged.isNotEmpty()) {
                // Pass latest query to refresh search results.
                viewModel.fetchSearch(query)
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(
                    onClick = {
                        query = ""
                        viewModel.clearSearch()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Clear Icon",
                    )
                }
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        placeholder = { Text(text = "Enter your breed") },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background, shape = RectangleShape)
    )
}
