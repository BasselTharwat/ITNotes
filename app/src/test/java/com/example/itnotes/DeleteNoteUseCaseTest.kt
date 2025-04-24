package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.DeleteNoteUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteNoteUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Before
    fun setup() {
        repository = mockk()
        deleteNoteUseCase = DeleteNoteUseCase(repository)
    }

    @Test
    fun `invoke should call deleteNote on repository`() = runTest {
        val note = Note(title = "To Delete", content = "Test content")
        coEvery { repository.deleteNote(note) } just Runs

        deleteNoteUseCase(note)

        coVerify { repository.deleteNote(note) }
    }
}
