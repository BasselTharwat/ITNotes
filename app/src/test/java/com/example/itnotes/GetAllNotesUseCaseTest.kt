package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.GetAllNotesUseCase
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
class GetAllNotesUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var getAllNotesUseCase: GetAllNotesUseCase

    @Before
    fun setup() {
        repository = mockk()
        getAllNotesUseCase = GetAllNotesUseCase(repository)
    }

    @Test
    fun `invoke should return flow of notes from repository`() = runTest {
        val notes = listOf(Note(title = "Note", content = "Sample"))
        every { repository.getAllNotes() } returns flowOf(notes)

        val result = getAllNotesUseCase().first()

        assertEquals(notes, result)
    }
}
