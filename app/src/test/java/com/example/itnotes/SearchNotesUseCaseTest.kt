package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.repository.NoteRepository
import com.example.itnotes.domain.usecase.SearchNotesUseCase
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
class SearchNotesUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var searchNotesUseCase: SearchNotesUseCase

    @Before
    fun setup() {
        repository = mockk()
        searchNotesUseCase = SearchNotesUseCase(repository)
    }

    @Test
    fun `invoke should return flow of filtered notes`() = runTest {
        val query = "search"
        val notes = listOf(Note(title = "search title", content = "something"))
        every { repository.searchNotes(query) } returns flowOf(notes)

        val result = searchNotesUseCase(query).first()

        assertEquals(notes, result)
    }
}
