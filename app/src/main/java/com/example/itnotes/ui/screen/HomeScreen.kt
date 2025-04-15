package com.example.itnotes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.itnotes.ui.components.NoteItem
import com.example.itnotes.ui.viewModel.HomeUiState
import com.example.itnotes.ui.viewModel.HomeViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.R


@Composable
fun HomeScreen(
    onAddNote: () -> Unit,
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel() 
) {
    val uiState by viewModel.homeUiState.collectAsState()

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
                topBar = {
                    HomeTopAppBar(
                        modifier = modifier,
                        onAddClick = onAddNote
                    )
                },
                modifier = modifier
            )
            { it ->
                LazyColumn(
                    contentPadding = it,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(it),
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

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val image = painterResource(id = R.drawable.john)
            Image(
                painter = image,
                contentDescription = null,
                modifier = modifier
                    .size(16.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text("Hello 👋 John!", style = MaterialTheme.typography.titleMedium)
        }


        FloatingActionButton(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = "Add Note")
        }


    }


}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onAddNote = {},
        onNoteClick = {}
    )
}

