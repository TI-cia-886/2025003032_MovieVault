package com.movievault.app.viewmodel

import com.movievault.app.data.network.dto.MovieSearchDto

sealed interface AddMovieUiState {
    data object Idle : AddMovieUiState
    data object Loading : AddMovieUiState
    data class SearchResults(
        val results: List<MovieSearchDto> = emptyList(),
        val query: String = "",
        val totalResults: Int = 0
    ) : AddMovieUiState
    data class DetailLoading(val imdbId: String) : AddMovieUiState
    data class MovieAdded(val title: String) : AddMovieUiState
    data class Error(val message: String) : AddMovieUiState
}
