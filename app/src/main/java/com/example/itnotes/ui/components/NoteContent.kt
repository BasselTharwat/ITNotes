package com.example.itnotes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.itnotes.ui.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteContent(
    note: Note,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel()
){

    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    val focusManager = LocalFocusManager.current

    // Auto update ViewModel on title/content change
    LaunchedEffect(title, content) {
        viewModel.updateNote(note.copy(title = title, content = content, lastModified = System.currentTimeMillis()))
    }

    Column(
        modifier = modifier
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
