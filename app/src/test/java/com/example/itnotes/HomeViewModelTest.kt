package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.usecase.GetAllNotesUseCase
import com.example.itnotes.domain.usecase.SearchNotesUseCase
import com.example.itnotes.ui.viewModel.HomeUiState
import com.example.itnotes.ui.viewModel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getAllNotesUseCase: GetAllNotesUseCase
    private lateinit var searchNotesUseCase: SearchNotesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getAllNotesUseCase = mockk()
        searchNotesUseCase = mockk()

        // Default return value so init {} doesn't crash
        coEvery { getAllNotesUseCase() } returns flowOf(emptyList())

        viewModel = HomeViewModel(getAllNotesUseCase, searchNotesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllNotes emits loading then success`() = runTest {
        val fakeNotes = listOf(Note(title = "Test", content = "Content"))

        coEvery { getAllNotesUseCase() } returns flow {
            emit(fakeNotes)
        }

        viewModel.getAllNotes()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeUiState.value
        assertTrue(state is HomeUiState.Success)
        assertEquals(fakeNotes, (state as HomeUiState.Success).notes)
    }

    @Test
    fun `searchNotes with blank query calls getAllNotes`() = runTest {
        val emptyList = emptyList<Note>()
        coEvery { getAllNotesUseCase() } returns flowOf(emptyList)

        viewModel.searchNotes("")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeUiState.value
        assertTrue(state is HomeUiState.Success)
        assertEquals(emptyList, (state as HomeUiState.Success).notes)
    }

    @Test
    fun `searchNotes with non-empty query emits success`() = runTest {
        val query = "test"
        val notes = listOf(Note(title = "test", content = "example"))

        coEvery { searchNotesUseCase(query) } returns flowOf(notes)

        viewModel.searchNotes(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeUiState.value
        assertTrue(state is HomeUiState.Success)
        assertEquals(notes, (state as HomeUiState.Success).notes)
    }

    @Test
    fun `getAllNotes emits error when exception is thrown`() = runTest {
        coEvery { getAllNotesUseCase() } returns flow {
            throw RuntimeException("Something went wrong")
        }

        viewModel.getAllNotes()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.homeUiState.value
        assertTrue(state is HomeUiState.Error)
    }
}
