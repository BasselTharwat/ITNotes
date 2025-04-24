package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.UpdateNoteUseCase
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
class UpdateNoteUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var updateNoteUseCase: UpdateNoteUseCase

    @Before
    fun setup() {
        repository = mockk()
        updateNoteUseCase = UpdateNoteUseCase(repository)
    }

    @Test
    fun `invoke should call updateNote on repository`() = runTest {
        val note = Note(id = 1, title = "Updated", content = "Updated content")
        coEvery { repository.updateNote(note) } just Runs

        updateNoteUseCase(note)

        coVerify { repository.updateNote(note) }
    }
}
