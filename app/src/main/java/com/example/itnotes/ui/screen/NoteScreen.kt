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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.data.local.Note
import com.example.itnotes.ui.components.NoteContent
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
            TopAppBar(
                title = { Text("Note") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (uiState is NoteUiState.Success) {
                            val note = (uiState as NoteUiState.Success).note
                            viewModel.updateNote(note)
                        }
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
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
                var title by remember { mutableStateOf(note.title) }
                var content by remember { mutableStateOf(note.content) }

                val focusManager = LocalFocusManager.current

                // Auto update ViewModel on title/content change
                LaunchedEffect(title, content) {
                    viewModel.updateNote(note.copy(title = title, content = content))
                }

                Column(
                    modifier = modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        decorationBox = { innerTextField ->
                            if (title.isEmpty()) {
                                Text("Title", style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                            }
                            innerTextField()
                        }
                    )

                    BasicTextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier
                            .fillMaxSize(),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        decorationBox = { innerTextField ->
                            if (content.isEmpty()) {
                                Text("Start writing...", style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant))
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}