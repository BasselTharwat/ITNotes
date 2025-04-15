package com.example.itnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.itnotes.ui.screen.HomeScreen
import com.example.itnotes.ui.screen.NoteScreen


@Composable
fun NotesNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onAddNote = {
                    navController.navigate(Screen.Note.createRoute(null))
                },
                onNoteClick = { noteId ->
                    navController.navigate(Screen.Note.createRoute(noteId))
                }
            )
        }

        composable(
            route = Screen.Note.route,
            arguments = listOf(navArgument("noteId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            NoteScreen(
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
