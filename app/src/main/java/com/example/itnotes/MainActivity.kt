package com.example.itnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.itnotes.navigation.NotesNavGraph
import com.example.itnotes.ui.theme.ITNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITNotesTheme {
                val navController = rememberNavController()
                NotesNavGraph(navController = navController)
            }
        }
    }
}

