package com.example.itnotes.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Note : Screen("note/{noteId}") {
        fun createRoute(noteId: Int?) = "note/${noteId ?: -1}"
    }
}
