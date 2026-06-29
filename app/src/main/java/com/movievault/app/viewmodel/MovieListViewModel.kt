package com.movievault.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.WatchStatus
import com.movievault.app.data.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _selectedFilter = MutableStateFlow("All")
    private val _sortOrder = MutableStateFlow("date_desc")
    private val _isGridView = MutableStateFlow(true)

    private var searchJob: Job? = null

    init {
        // Load preferences first (doesn't trigger combine)
        loadPreferences()

        // Single reactive pipeline for movies with real-time counts
        viewModelScope.launch {
            combine(
                _selectedFilter,
                _searchQuery,
                _sortOrder
            ) { filter, query, sort ->
                Triple(filter, query, sort)
            }.flatMapLatest { (filter, query, sort) ->
                Log.d("MovieListVM", "flatMapLatest: filter=$filter query=$query sort=$sort")
                // Always observe all movies to compute counts, but filter/sort for display
                repository.getAllMovies().map { allMovies ->
                    val filteredMovies = when {
                        query.isNotBlank() -> allMovies.filter { it.title.contains(query, ignoreCase = true) }
                        filter == "Favorites" -> allMovies.filter { it.isFavorite }
                        filter == WatchStatus.WATCHED -> allMovies.filter { it.watchStatus == WatchStatus.WATCHED }
                        filter == WatchStatus.WATCHING -> allMovies.filter { it.watchStatus == WatchStatus.WATCHING }
                        filter == WatchStatus.WANT_TO_WATCH -> allMovies.filter { it.watchStatus == WatchStatus.WANT_TO_WATCH }
                        else -> allMovies
                    }
                    val sortedMovies = when (sort) {
                        "date_asc" -> filteredMovies.sortedBy { it.createdAt }
                        "date_desc" -> filteredMovies.sortedByDescending { it.createdAt }
                        "rating_desc" -> filteredMovies.sortedByDescending { it.imdbRating.toFloatOrNull() ?: 0f }
                        "rating_asc" -> filteredMovies.sortedBy { it.imdbRating.toFloatOrNull() ?: 0f }
                        "user_rating" -> filteredMovies.sortedByDescending { it.userRating }
                        else -> filteredMovies.sortedByDescending { it.createdAt }
                    }
                    // Compute real-time counts from all movies
                    val total = allMovies.size
                    val watched = allMovies.count { it.watchStatus == WatchStatus.WATCHED }
                    val wantToWatch = allMovies.count { it.watchStatus == WatchStatus.WANT_TO_WATCH }
                    val watching = allMovies.count { it.watchStatus == WatchStatus.WATCHING }
                    Triple(sortedMovies, total, Triple(watched, wantToWatch, watching))
                }
            }.collect { (movies, totalCount, counts) ->
                val (watchedCount, wantToWatchCount, watchingCount) = counts
                Log.d("MovieListVM", "collect: size=${movies.size} total=$totalCount watched=$watchedCount")
                _uiState.value = MovieListUiState.Success(
                    movies = movies,
                    selectedFilter = _selectedFilter.value,
                    searchQuery = _searchQuery.value,
                    isGridView = _isGridView.value,
                    sortOrder = _sortOrder.value,
                    totalCount = totalCount,
                    watchedCount = watchedCount,
                    wantToWatchCount = wantToWatchCount,
                    watchingCount = watchingCount
                )
            }
        }
    }

    // Prevent preference collect from overwriting user actions
    private var preferencesLoaded = false

    private fun loadPreferences() {
        viewModelScope.launch {
            repository.isGridViewMode.collect { isGrid ->
                if (!preferencesLoaded) {
                    _isGridView.value = isGrid
                }
            }
        }
        viewModelScope.launch {
            repository.sortOrder.collect { order ->
                if (!preferencesLoaded) {
                    _sortOrder.value = order
                }
            }
        }
        viewModelScope.launch {
            repository.lastSearchQuery.collect { query ->
                if (!preferencesLoaded && query.isNotBlank()) {
                    _searchQuery.value = query
                }
            }
        }
        // Mark preferences loaded after a short delay to allow initial values to settle
        viewModelScope.launch {
            delay(100)
            preferencesLoaded = true
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            if (query.isBlank()) {
                repository.setLastSearchQuery("")
            }
        }
    }

    fun onFilterChange(filter: String) {
        _selectedFilter.value = filter
        viewModelScope.launch {
            repository.setDefaultCategory(filter)
        }
    }

    fun onSortOrderChange(order: String) {
        _sortOrder.value = order
        viewModelScope.launch {
            repository.setSortOrder(order)
        }
    }

    fun onToggleViewMode() {
        _isGridView.value = !_isGridView.value
        // Immediately update current UI state so view mode switches instantly
        val current = _uiState.value
        if (current is MovieListUiState.Success) {
            _uiState.value = current.copy(isGridView = _isGridView.value)
        }
        viewModelScope.launch {
            repository.setGridViewMode(_isGridView.value)
        }
    }

    fun onToggleFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(movie.id)
        }
    }

    fun onUpdateWatchStatus(movie: MovieEntity, status: String) {
        viewModelScope.launch {
            repository.updateWatchStatus(movie.id, status)
        }
    }

    fun onDeleteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    fun onRefresh() {
        // Reset search and filter to show all movies when returning to main screen
        _searchQuery.value = ""
        _selectedFilter.value = "All"
        viewModelScope.launch {
            repository.setLastSearchQuery("")
            repository.setDefaultCategory("All")
        }
    }

    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieListViewModel(repository) as T
        }
    }
}
