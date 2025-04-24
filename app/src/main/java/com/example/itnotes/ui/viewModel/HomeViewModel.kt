package com.example.itnotes.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itnotes.data.local.Note
import com.example.itnotes.domain.usecase.CreateNoteUseCase
import com.example.itnotes.domain.usecase.GetAllNotesUseCase
import com.example.itnotes.domain.usecase.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val searchNotesUseCase: SearchNotesUseCase
) : ViewModel(){

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    // Another state for search
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        getAllNotes()
    }

    fun getAllNotes() {
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

    fun searchNotes(query: String) {
        if (query.isBlank()) {
            getAllNotes()
            return
        }

        searchNotesUseCase(query)
            .onStart {
                _homeUiState.value = HomeUiState.Loading
            }
            .catch {
                _homeUiState.value = HomeUiState.Error
            }
            .onEach { notes ->
                _homeUiState.value = HomeUiState.Success(notes)
            }
            .launchIn(viewModelScope)
    }

    // Search methods
    fun startSearch() {
        _isSearching.value = true
    }

    fun endSearch() {
        _isSearching.value = false
        _searchQuery.value = ""
        getAllNotes()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        searchNotes(_searchQuery.value)
    }


}