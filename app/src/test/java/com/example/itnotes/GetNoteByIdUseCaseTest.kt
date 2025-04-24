package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.GetNoteByIdUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNoteByIdUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        getNoteByIdUseCase = GetNoteByIdUseCase(repository)
    }

    @Test
    fun `invoke should return flow of note by id from repository`() = runTest {
        val note = Note(id = 1, title = "Single Note", content = "Detail")
        every { repository.getNoteById(1) } returns flowOf(note)

        val result = getNoteByIdUseCase(1).first()

        assertEquals(note, result)
    }
}
