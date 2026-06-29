package com.movievault.app.viewmodel

import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.network.dto.MovieSearchDto

sealed interface MovieListUiState {
    data object Loading : MovieListUiState
    data class Success(
        val movies: List<MovieEntity> = emptyList(),
        val searchResults: List<MovieSearchDto> = emptyList(),
        val isSearching: Boolean = false,
        val searchQuery: String = "",
        val selectedFilter: String = "All",
        val isGridView: Boolean = true,
        val sortOrder: String = "date_desc",
        val totalCount: Int = 0,
        val watchedCount: Int = 0,
        val wantToWatchCount: Int = 0,
        val watchingCount: Int = 0
    ) : MovieListUiState
    data class Error(val message: String) : MovieListUiState
}
