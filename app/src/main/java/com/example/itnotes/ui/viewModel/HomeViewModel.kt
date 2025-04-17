package com.example.itnotes.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.usecase.CreateNoteUseCase
import com.example.itnotes.domain.usecase.GetAllNotesUseCase
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


sealed interface HomeUiState{
    data class Success(val notes: List<Note>): HomeUiState
    object Error: HomeUiState
    object Loading: HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel(){

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getAllNotesUseCase()
            .onStart {
                _homeUiState.value = HomeUiState.Loading
            }
            .catch {
                _homeUiState.value = HomeUiState.Error
            }
            .onEach {
                _homeUiState.value = HomeUiState.Success(it)
            }
            .launchIn(viewModelScope)
    }
}