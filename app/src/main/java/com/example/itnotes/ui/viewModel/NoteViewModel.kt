package com.example.itnotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.usecase.CreateNoteUseCase
import com.example.itnotes.domain.usecase.DeleteNoteUseCase
import com.example.itnotes.domain.usecase.GetNoteByIdUseCase
import com.example.itnotes.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface NoteUiState{
    data class Success(val note: Note): NoteUiState
    object Error: NoteUiState
    object Loading: NoteUiState
}

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel(){

    private val _noteUiState = MutableStateFlow<NoteUiState>(NoteUiState.Loading)
    val noteUiState: StateFlow<NoteUiState> = _noteUiState.asStateFlow()

    fun getNoteById(noteId: Int) {
        getNoteByIdUseCase(noteId)
            .onStart {
                _noteUiState.value = NoteUiState.Loading
            }
            .catch {
                _noteUiState.value = NoteUiState.Error
            }
            .onEach { note ->
                if (note == null) {
                    _noteUiState.value = NoteUiState.Error
                } else {
                    _noteUiState.value = NoteUiState.Success(note)
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                updateNoteUseCase(note)

            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                deleteNoteUseCase(note)
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error
            }
        }
    }

    fun createNote(note: Note){
        viewModelScope.launch {
            try {
                val newNodeId = createNoteUseCase(note)
                getNoteById(newNodeId)
            } catch (e: Exception) {
                _noteUiState.value = NoteUiState.Error
            }
        }
    }


}


