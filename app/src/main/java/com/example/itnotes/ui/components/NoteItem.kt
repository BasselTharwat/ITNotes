package com.example.itnotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.itnotes.data.local.Note
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun NoteItem(note: Note,
             onClick: () -> Unit,
             modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),

    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = HtmlCompat.fromHtml(note.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Created on: ${SimpleDateFormat("EEE, hh:mm a", Locale.getDefault()).format(
                    Date(note.createdAt)
                )}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
