package com.movievault.app.viewmodel

import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.ReviewEntity

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(
        val movie: MovieEntity,
        val reviews: List<ReviewEntity> = emptyList(),
        val averageRating: Float = 0f,
        val reviewCount: Int = 0
    ) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}
