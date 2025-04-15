package com.example.itnotes.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.ui.viewModel.NoteUiState
import com.example.itnotes.ui.viewModel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteId: Int?,
    onBack: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.noteUiState.collectAsState()

    LaunchedEffect(noteId) {
        if (noteId == null || noteId == -1) {
            viewModel.setEmptyNote()
        } else {
            viewModel.getNoteById(noteId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is NoteUiState.Loading -> { /* ... */ }
            is NoteUiState.Error -> { /* ... */ }
            is NoteUiState.Empty -> { /* new note UI */ }
            is NoteUiState.Success -> {
                val note = (uiState as NoteUiState.Success).note
                // UI to show/edit note
            }
        }
    }
}