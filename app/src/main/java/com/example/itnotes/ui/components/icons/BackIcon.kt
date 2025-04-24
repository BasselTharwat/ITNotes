package com.example.itnotes.ui.components.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.itnotes.R

@Composable
fun BackIcon(onBack: () -> Unit, modifier: Modifier) {
    IconButton(onClick = onBack) {
        Icon(
            painter = painterResource(R.drawable.arrowback),
            contentDescription = "Back",
            modifier = modifier.padding(top = 8.dp)
        )
    }
}