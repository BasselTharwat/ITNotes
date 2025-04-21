package com.example.itnotes.domain.repository

import com.example.itnotes.data.local.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Int): Flow<Note?>
    suspend fun insertNote(note: Note) : Long
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    fun searchNotes(query: String): Flow<List<Note>>
}
