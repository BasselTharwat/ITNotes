package com.example.itnotes.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.itnotes.R
import com.example.itnotes.data.local.Note
import com.example.itnotes.ui.components.NoteContent
import com.example.itnotes.ui.components.TagEditorBottomSheet
import com.example.itnotes.ui.components.icons.BackIcon
import com.example.itnotes.ui.components.icons.DeleteIcon
import com.example.itnotes.ui.components.icons.TagIcon
import com.example.itnotes.ui.theme.onPrimaryDark
import com.example.itnotes.ui.theme.primaryLight
import com.example.itnotes.ui.viewModel.NoteUiState
import com.example.itnotes.ui.viewModel.NoteViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun NoteScreen(
    noteId: Int?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.noteUiState.collectAsState()
    var showTagSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current




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

                        // Check if the note is empty (no title, no content)
                        if (note.title.isBlank() && note.content.isBlank()) {
                            // Show a toast that the empty note is discarded
                            Toast.makeText(context, "Empty note discarded", Toast.LENGTH_SHORT).show()

                            // Delete the note if it has no title and content
                            viewModel.deleteNote(note)
                        } else {
                            // Update the note if it has a title or content
                            viewModel.updateNote(note)
                        }
                    }
                    onBack()
                },
                onDelete = {
                    if (uiState is NoteUiState.Success) {
                        val note = (uiState as NoteUiState.Success).note
                        viewModel.deleteNote(note)
                        onBack()


                    }
                },
                onAddTags = {
                    showTagSheet = true
                }
            )
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
                    modifier = modifier.padding(
                        start = padding.calculateStartPadding(LocalLayoutDirection.current),
                        top = padding.calculateTopPadding(),
                        end = padding.calculateEndPadding(LocalLayoutDirection.current),
                        bottom = 0.dp
                    )

                )
            }
        }

        if (showTagSheet && uiState is NoteUiState.Success) {
            val note = (uiState as NoteUiState.Success).note
            TagEditorBottomSheet(
                currentTags = note.tags,
                onDismiss = { updatedTags ->
                    viewModel.updateNote(note.copy(tags = updatedTags))
                    showTagSheet = false },
                onSave = { updatedTags ->
                    viewModel.updateNote(note.copy(tags = updatedTags))
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
        title ={},
        navigationIcon = {
            BackIcon(onBack, modifier)
        },
        actions = {
            TagIcon(onAddTags)

            Spacer(modifier = modifier.padding(4.dp))

            DeleteIcon(onDelete)

            Spacer(modifier = modifier.padding(4.dp))


        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}







