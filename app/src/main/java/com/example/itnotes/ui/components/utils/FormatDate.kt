package com.example.itnotes.ui.components.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(dateMillis: Long): String {
    val date = Date(dateMillis)
    val calendar = Calendar.getInstance().apply { timeInMillis = dateMillis }

    val now = Calendar.getInstance()

    // Check if it's today
    val sameDay = calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)

    // Check if it's the same year
    val sameYear = calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)

    return when {
        sameDay -> SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date) // Today
        sameYear -> SimpleDateFormat("d MMM", Locale.getDefault()).format(date) // Same Year
        else -> SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(date) // Different Year
    }
}
