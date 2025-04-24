package com.example.itnotes

import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.usecase.CreateNoteUseCase
import com.example.itnotes.domain.usecase.DeleteNoteUseCase
import com.example.itnotes.domain.usecase.GetNoteByIdUseCase
import com.example.itnotes.domain.usecase.UpdateNoteUseCase
import com.example.itnotes.ui.viewModel.NoteUiState
import com.example.itnotes.ui.viewModel.NoteViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var updateNoteUseCase: UpdateNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var createNoteUseCase: CreateNoteUseCase
    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getNoteByIdUseCase = mockk()
        updateNoteUseCase = mockk(relaxed = true)
        deleteNoteUseCase = mockk(relaxed = true)
        createNoteUseCase = mockk()
        viewModel = NoteViewModel(getNoteByIdUseCase, updateNoteUseCase, deleteNoteUseCase, createNoteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getNoteById emits Success when note is found`() = runTest {
        val note = Note(title = "Test", content = "Text")
        every { getNoteByIdUseCase(1) } returns flowOf(note)

        viewModel.getNoteById(1)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.noteUiState.value
        assertTrue(state is NoteUiState.Success)
        assertEquals(note, (state as NoteUiState.Success).note)
    }

    @Test
    fun `createNote calls useCase and emits Success`() = runTest {
        val note = Note(title = "New", content = "Note")
        coEvery { createNoteUseCase(note) } returns 1
        every { getNoteByIdUseCase(1) } returns flowOf(note)

        viewModel.createNote(note)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.noteUiState.value
        assertTrue(state is NoteUiState.Success)
    }
}
