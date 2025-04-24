package com.example.itnotes.ui.components

import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.itnotes.ui.components.icons.AddTagIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagEditorBottomSheet(
    currentTags: List<String>,
    onDismiss: (List<String>) -> Unit,
    onSave: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTags = remember { mutableStateListOf(*currentTags.toTypedArray()) }
    var newTag by remember { mutableStateOf("") }
    val context = LocalContext.current


    ModalBottomSheet(
        modifier = modifier
            .padding(16.dp),
        onDismissRequest = { onDismiss(selectedTags.toList()) },
        dragHandle =  null ,
        shape = RectangleShape
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(4.dp, MaterialTheme.colorScheme.onSurfaceVariant, RectangleShape)
        ){
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState())
                    .imePadding()
            ) {
                Text("Edit Tags", style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(top = 8.dp, bottom = 8.dp))

                Spacer(modifier = modifier.height(8.dp))

                if (selectedTags.isNotEmpty()) {
                    selectedTags.forEach { tag ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = tag,
                                modifier = modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            IconButton(
                                onClick = { selectedTags.remove(tag) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove tag"
                                )
                            }
                        }
                    }

                    Spacer(modifier = modifier.height(16.dp))
                }

                OutlinedTextField(
                    shape = RectangleShape,
                    value = newTag,
                    onValueChange = { newTag = it },
                    label = { Text("Add new tag") },
                    modifier = modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        val trimmed = newTag.trim()
                        if (trimmed.isNotBlank()) {
                            if (trimmed !in selectedTags) {
                                selectedTags.add(trimmed)
                                newTag = ""
                            } else {
                                Toast.makeText(context, "Tag already exists", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )

                Spacer(modifier = modifier.height(16.dp))

                IconButton(
                    enabled = newTag.isNotBlank(),
                    onClick = {
                        val trimmed = newTag.trim()
                        when {
                            trimmed.isBlank() -> { }
                            trimmed in selectedTags -> {
                                Toast.makeText(context, "Tag already exists", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                selectedTags.add(trimmed)
                                newTag = ""
                                onSave(selectedTags.toList())
                            }
                        }
                    },
                    modifier = modifier.align(Alignment.End)
                ) {
                    AddTagIcon()
                }


        }

        }
    }
}


