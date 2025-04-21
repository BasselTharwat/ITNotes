package com.example.itnotes.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Int): Flow<Note?>

    @Query("""
        SELECT * FROM notes 
        WHERE (title LIKE '%' || :query || '%' 
            OR content LIKE '%' || :query || '%'
            OR tags LIKE '%' || :query || '%')
        ORDER BY createdAt DESC
    """)
    fun searchNotes(query: String): Flow<List<Note>>
}
