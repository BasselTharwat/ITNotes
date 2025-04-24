package com.example.itnotes.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.itnotes.data.local.Note
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat
import com.example.itnotes.ui.components.utils.formatDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun NoteItem(note: Note,
             onClick: () -> Unit,
             modifier: Modifier = Modifier) {

    val formattedModifiedDate = remember(note.lastModified) {
        formatDate(note.lastModified)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .clickable { onClick() }
            .border(4.dp, MaterialTheme.colorScheme.onSurface, RectangleShape),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)

    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()

            ) {
                Text(
                    text = note.title.ifBlank { "Untitled" },
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (note.title.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )


                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)

            }


            Text(
                text = formattedModifiedDate,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
            Text(
                text = HtmlCompat.fromHtml(note.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (note.content.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
}
