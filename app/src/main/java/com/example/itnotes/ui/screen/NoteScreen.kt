package com.example.itnotes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.data.local.Note
import com.example.itnotes.ui.components.NoteContent
import com.example.itnotes.ui.components.TagEditorBottomSheet
import com.example.itnotes.ui.viewModel.NoteUiState
import com.example.itnotes.ui.viewModel.NoteViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteId: Int?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.noteUiState.collectAsState()
    var showTagSheet by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId == null || noteId == -1) {
            val newNote = Note(
                title = "",
                content = ""
            )
            viewModel.createNote(newNote)
        } else {
            viewModel.getNoteById(noteId)
        }
    }

    Scaffold(
        topBar = {
            NoteTopAppBar(
                onBack = {
                    if (uiState is NoteUiState.Success) {
                        val note = (uiState as NoteUiState.Success).note
                        viewModel.updateNote(note)
                    }
                    onBack()
                },
                onDelete = {
                    if (uiState is NoteUiState.Success) {
                        val note = (uiState as NoteUiState.Success).note
                        viewModel.deleteNote(note)
                        onBack() // Automatically navigate back to the HomeScreen after deletion
                    }
                },
                onAddTags = {
                    showTagSheet = true
                }
            )
        },
        bottomBar = {
            if (uiState is NoteUiState.Success) {
                val note = (uiState as NoteUiState.Success).note
                val formattedDate = remember(note.lastModified) {
                    val sdf = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
                    sdf.format(Date(note.lastModified))
                }

                BottomAppBar {
                    Text(
                        text = "Last modified: $formattedDate",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        },
        modifier = modifier
    ) { padding ->
        when (uiState) {
            is NoteUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is NoteUiState.Error -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("An error occurred", color = MaterialTheme.colorScheme.error)
                }
            }
            is NoteUiState.Success -> {
                val note = (uiState as NoteUiState.Success).note

                NoteContent(
                    note = note,
                    viewModel = viewModel,
                    modifier = modifier.padding(padding)
                )
            }
        }

        if (showTagSheet && uiState is NoteUiState.Success) {
            val note = (uiState as NoteUiState.Success).note
            TagEditorBottomSheet(
                currentTags = note.tags,
                onDismiss = { showTagSheet = false },
                onSave = { updatedTags ->
                    viewModel.updateNote(note.copy(tags = updatedTags))
                    showTagSheet = false
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onAddTags: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Note") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
            IconButton(onClick = onAddTags) {
                Icon(Icons.Filled.Star, contentDescription = "Add Tags")
            }
        },
        modifier = modifier
    )
}



