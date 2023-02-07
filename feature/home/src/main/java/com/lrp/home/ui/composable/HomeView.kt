package com.lrp.home.ui.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(photoList: List<String>) {
    val state = rememberLazyListState()
    MaterialTheme {
        Scaffold(
            content = {
                LazyItemsView(state, photoList)
            }
        )
    }
}

@Composable
fun LazyItemsView(state: LazyListState, photoList: List<String>) {
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = photoList,
            itemContent = {
                PhotoView(it)
            }
        )
    }
}

@Composable
fun PhotoView(link: String) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        AsyncImage(
            model = link,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(65.dp)
        )
    }
}