package com.swanand.fetchviewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(viewModel: ListViewModel, modifier: Modifier = Modifier) {

    val colors = listOf(
        Color(0xFFFFCDD2), // Light Red
        Color(0xFFC8E6C9), // Light Green
        Color(0xFFBBDEFB), // Light Blue
        Color(0xFFD1C4E9)  // Light Purple
    )

    val items by viewModel.items.collectAsState()
    val fetchState by viewModel.progressStateFlow.collectAsState()
    Scaffold { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (fetchState) {
                ListViewModel.FetchState.Loading -> {
                    CircularProgressIndicator()
                }

                ListViewModel.FetchState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Failed to fetch data.\nPlease try again.",
                            color = Color.Red,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.retryFetch() }) {
                            Text("Retry")
                        }
                    }
                }

                ListViewModel.FetchState.Success -> {

                    LazyColumn(
                        modifier = modifier.padding(paddingValues)
                            .fillMaxWidth()
                            .background(Color.Red),
                        verticalArrangement = Arrangement.Center
                    ) {
                        items.forEach { (listId, itemList) ->
                            val idColor = colors[listId % colors.size]
                            val itemColors = arrayOf(
                                Color(
                                    ColorUtils.blendARGB(
                                        idColor.toArgb(),
                                        Color.DarkGray.toArgb(),
                                        0.1f
                                    )
                                ),
                                Color(
                                    ColorUtils.blendARGB(
                                        idColor.toArgb(),
                                        Color.White.toArgb(),
                                        0.5f
                                    )
                                )
                            )

                            stickyHeader {
                                Text(
                                    text = "List ID $listId",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(idColor)
                                        .padding(16.dp)
                                )
                            }
                            itemsIndexed(itemList) { index, item -> // Using key for performance
                                Text(
                                    text = "${item.name}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(itemColors[index % 2])
                                        .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
