package com.example.itnotes.domain.usecase

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() : Flow<List<Note>> = noteRepository.getAllNotes()
}