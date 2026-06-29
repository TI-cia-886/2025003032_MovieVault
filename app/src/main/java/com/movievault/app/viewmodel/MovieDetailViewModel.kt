package com.movievault.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.ReviewEntity
import com.movievault.app.data.entity.WatchStatus
import com.movievault.app.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val movieId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
        observeReviews()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            val movie = repository.getMovieById(movieId)
            if (movie != null) {
                val avgRating = repository.getAverageRating(movieId)
                val reviewCount = repository.getReviewCount(movieId)
                _uiState.update {
                    MovieDetailUiState.Success(
                        movie = movie,
                        averageRating = avgRating,
                        reviewCount = reviewCount
                    )
                }
            } else {
                _uiState.value = MovieDetailUiState.Error("Movie not found")
            }
        }
    }

    private fun observeReviews() {
        viewModelScope.launch {
            repository.getReviewsForMovie(movieId).collect { reviews ->
                val currentState = _uiState.value
                if (currentState is MovieDetailUiState.Success) {
                    val avgRating = repository.getAverageRating(movieId)
                    _uiState.update {
                        MovieDetailUiState.Success(
                            movie = currentState.movie,
                            reviews = reviews,
                            averageRating = avgRating,
                            reviewCount = reviews.size
                        )
                    }
                }
            }
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state is MovieDetailUiState.Success) {
                repository.toggleFavorite(state.movie.id)
                val updated = repository.getMovieById(movieId)
                if (updated != null) {
                    _uiState.update {
                        MovieDetailUiState.Success(
                            movie = updated,
                            reviews = state.reviews,
                            averageRating = state.averageRating,
                            reviewCount = state.reviewCount
                        )
                    }
                }
            }
        }
    }

    fun onUpdateWatchStatus(status: String) {
        viewModelScope.launch {
            val state = _uiState.value
            if (state is MovieDetailUiState.Success) {
                repository.updateWatchStatus(state.movie.id, status)
                val updated = repository.getMovieById(movieId)
                if (updated != null) {
                    _uiState.update {
                        MovieDetailUiState.Success(
                            movie = updated,
                            reviews = state.reviews,
                            averageRating = state.averageRating,
                            reviewCount = state.reviewCount
                        )
                    }
                }
            }
        }
    }

    fun onUpdateUserRating(rating: Float) {
        viewModelScope.launch {
            val state = _uiState.value
            if (state is MovieDetailUiState.Success) {
                repository.updateUserRating(state.movie.id, rating)
                val updated = repository.getMovieById(movieId)
                if (updated != null) {
                    _uiState.update {
                        MovieDetailUiState.Success(
                            movie = updated,
                            reviews = state.reviews,
                            averageRating = state.averageRating,
                            reviewCount = state.reviewCount
                        )
                    }
                }
            }
        }
    }

    fun onAddReview(content: String, rating: Float) {
        viewModelScope.launch {
            val review = ReviewEntity(
                movieId = movieId,
                content = content,
                rating = rating
            )
            repository.addReview(review)
        }
    }

    fun onDeleteReview(review: ReviewEntity) {
        viewModelScope.launch {
            repository.deleteReview(review)
        }
    }

    fun onDeleteMovie() {
        viewModelScope.launch {
            repository.deleteMovieById(movieId)
        }
    }

    class Factory(
        private val repository: MovieRepository,
        private val movieId: Long
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(repository, movieId) as T
        }
    }
}
