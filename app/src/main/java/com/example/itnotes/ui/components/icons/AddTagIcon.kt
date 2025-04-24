package com.example.itnotes.ui.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun AddTagIcon() {
    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.onSurface, shape = RectangleShape)
            .background(MaterialTheme.colorScheme.inversePrimary, shape = RectangleShape)
            .padding(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Tag",
            tint = MaterialTheme.colorScheme.onSurface,

        )
    }
}