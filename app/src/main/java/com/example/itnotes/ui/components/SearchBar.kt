package com.example.itnotes.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface, RectangleShape)
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.surface),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onSurface),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (query.isNotBlank()) {
                            onSearch()
                            focusManager.clearFocus()
                        }
                    }
                ),
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text("Search notes...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.outlineVariant
                            ),)
                    }
                    innerTextField()
                }
            )

            IconButton(onClick = onClose)
                 {
                Icon(Icons.Default.Close, contentDescription = "Close search")
            }


        }
    }
}

