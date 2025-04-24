package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.CreateNoteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateNoteUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var createNoteUseCase: CreateNoteUseCase

    @Before
    fun setup() {
        repository = mockk()
        createNoteUseCase = CreateNoteUseCase(repository)
    }

    @Test
    fun `invoke should return note ID when repository inserts note`() = runTest {
        val note = Note(title = "Title", content = "Content")
        coEvery { repository.insertNote(note) } returns 42L

        val result = createNoteUseCase(note)

        assertEquals(42, result)
        coVerify { repository.insertNote(note) }
    }
}
