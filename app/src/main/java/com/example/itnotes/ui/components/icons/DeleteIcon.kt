package com.example.itnotes.ui.components.icons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.itnotes.R

@Composable
fun DeleteIcon(onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.error, shape = RectangleShape)
            .padding(4.dp) // Padding between border and icon
            .clickable(onClick = onDelete) // Make Box clickable
    ) {
        Icon(
            painter = painterResource(R.drawable.deletesvg2),
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error
        )
    }
}