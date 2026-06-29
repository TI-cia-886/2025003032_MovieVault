package com.movievault.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movievault.app.data.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddMovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddMovieUiState>(AddMovieUiState.Idle)
    val uiState: StateFlow<AddMovieUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        if (query.isBlank()) {
            _uiState.value = AddMovieUiState.Idle
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = AddMovieUiState.Loading
            delay(500) // debounce

            repository.searchMoviesOnline(query).fold(
                onSuccess = { results ->
                    _uiState.value = AddMovieUiState.SearchResults(
                        results = results,
                        query = query
                    )
                },
                onFailure = { error ->
                    _uiState.value = AddMovieUiState.Error(
                        error.message ?: "Search failed"
                    )
                }
            )
        }
    }

    fun onAddMovie(imdbId: String) {
        viewModelScope.launch {
            _uiState.value = AddMovieUiState.DetailLoading(imdbId)

            // Check if already in collection
            val existing = repository.getMovieByImdbId(imdbId)
            if (existing != null) {
                _uiState.value = AddMovieUiState.Error("This movie is already in your collection")
                return@launch
            }

            repository.saveMovieFromOnline(imdbId).fold(
                onSuccess = {
                    val movie = repository.getMovieById(it)
                    _uiState.value = AddMovieUiState.MovieAdded(
                        movie?.title ?: "Unknown"
                    )
                },
                onFailure = { error ->
                    _uiState.value = AddMovieUiState.Error(
                        error.message ?: "Failed to add movie"
                    )
                }
            )
        }
    }

    fun onResetState() {
        _uiState.value = AddMovieUiState.Idle
    }

    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddMovieViewModel(repository) as T
        }
    }
}
