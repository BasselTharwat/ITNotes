package com.example.itnotes.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.itnotes.ui.components.NoteItem
import com.example.itnotes.ui.viewModel.HomeUiState
import com.example.itnotes.ui.viewModel.HomeViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.R
import com.example.itnotes.ui.components.SearchBar
import com.example.itnotes.ui.components.icons.AddNoteIcon
import com.example.itnotes.ui.components.icons.SearchIcon


@Composable
fun HomeScreen(
    onAddNote: () -> Unit,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel() 
) {
    val uiState by viewModel.homeUiState.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    when (uiState) {
        is HomeUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("An error occurred", color = MaterialTheme.colorScheme.error)
            }
        }

        is HomeUiState.Success -> {
            val notes = (uiState as HomeUiState.Success).notes

            Scaffold (
                topBar =
                {
                    if (isSearching) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            onSearch = {
                                viewModel.performSearch()
                            },
                            onClose = {
                                viewModel.endSearch()
                            }
                        )
                    } else {
                        HomeTopAppBar(
                            modifier = modifier
                                .padding(bottom = 8.dp),
                            onAddClick = onAddNote,
                            onSearchClick = { viewModel.startSearch() }
                        )
                    }

                },
                modifier = modifier
            )
            {
                if (notes.isEmpty() && isSearching && searchQuery.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No notes found for \"$searchQuery\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = it,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(notes) { note ->
                            NoteItem(note = note,
                                onClick = { onNoteClick(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                val image = painterResource(id = R.drawable.john)
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = modifier
                        .size(64.dp)
                        .border(4.dp, MaterialTheme.colorScheme.onSurface, RectangleShape)
                )

                Spacer(modifier = modifier.width(8.dp))
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Hello \uD83D\uDC4B ",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text("John!", style = MaterialTheme.typography.titleLarge)
                }
            }

            Row(modifier = modifier) {
                SearchIcon(onSearchClick)
                Spacer(modifier = modifier.width(8.dp))
                AddNoteIcon(onAddClick)
            }
        }
    }
}
