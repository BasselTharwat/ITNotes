package com.example.itnotes.domain.usecase

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = noteRepository.deleteNote(note)
}