package com.example.itnotes.data.repository

import com.example.itnotes.data.local.Note
import com.example.itnotes.data.local.NoteDao
import com.example.itnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository{
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNoteById(id: Int): Flow<Note?> = noteDao.getNoteById(id)

    override suspend fun insertNote(note: Note) : Long = noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)


}