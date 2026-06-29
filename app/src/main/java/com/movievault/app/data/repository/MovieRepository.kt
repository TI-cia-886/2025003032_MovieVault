package com.movievault.app.data.repository

import com.movievault.app.data.dao.MovieDao
import com.movievault.app.data.dao.ReviewDao
import com.movievault.app.data.entity.MovieEntity
import com.movievault.app.data.entity.ReviewEntity
import com.movievault.app.data.entity.WatchStatus
import com.movievault.app.data.network.NetworkDataSource
import com.movievault.app.data.network.dto.MovieSearchDto
import com.movievault.app.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDao: MovieDao,
    private val reviewDao: ReviewDao,
    private val networkDataSource: NetworkDataSource,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    // ===== Local Movie Operations =====

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun getMoviesByWatchStatus(status: String): Flow<List<MovieEntity>> =
        movieDao.getMoviesByWatchStatus(status)

    fun searchMovies(query: String): Flow<List<MovieEntity>> = movieDao.searchMovies(query)

    fun getMoviesByGenre(genre: String): Flow<List<MovieEntity>> = movieDao.getMoviesByGenre(genre)

    suspend fun getMovieById(id: Long): MovieEntity? = movieDao.getMovieById(id)

    suspend fun getMovieByImdbId(imdbId: String): MovieEntity? = movieDao.getMovieByImdbId(imdbId)

    suspend fun insertMovie(movie: MovieEntity): Long = movieDao.insertMovie(movie)

    suspend fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)

    suspend fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovie(movie)

    suspend fun deleteMovieById(id: Long) = movieDao.deleteMovieById(id)

    suspend fun getMovieCount(): Int = movieDao.getMovieCount()

    suspend fun getMovieCountByStatus(status: String): Int = movieDao.getMovieCountByStatus(status)

    suspend fun toggleFavorite(movieId: Long) {
        val movie = movieDao.getMovieById(movieId) ?: return
        movieDao.updateMovie(movie.copy(isFavorite = !movie.isFavorite, updatedAt = System.currentTimeMillis()))
    }

    suspend fun updateWatchStatus(movieId: Long, status: String) {
        val movie = movieDao.getMovieById(movieId) ?: return
        movieDao.updateMovie(movie.copy(watchStatus = status, updatedAt = System.currentTimeMillis()))
    }

    suspend fun updateUserRating(movieId: Long, rating: Float) {
        val movie = movieDao.getMovieById(movieId) ?: return
        movieDao.updateMovie(movie.copy(userRating = rating, updatedAt = System.currentTimeMillis()))
    }

    suspend fun getAllGenres(): List<String> {
        val genres = movieDao.getAllGenres()
        val allGenres = mutableSetOf<String>()
        genres.forEach { genreStr ->
            genreStr.split(",").forEach { g ->
                val trimmed = g.trim()
                if (trimmed.isNotEmpty()) allGenres.add(trimmed)
            }
        }
        return allGenres.sorted()
    }

    // ===== Review Operations =====

    fun getReviewsForMovie(movieId: Long): Flow<List<ReviewEntity>> =
        reviewDao.getReviewsForMovie(movieId)

    suspend fun addReview(review: ReviewEntity): Long = reviewDao.insertReview(review)

    suspend fun updateReview(review: ReviewEntity) = reviewDao.updateReview(review)

    suspend fun deleteReview(review: ReviewEntity) = reviewDao.deleteReview(review)

    suspend fun getAverageRating(movieId: Long): Float {
        return reviewDao.getAverageRatingForMovie(movieId) ?: 0f
    }

    suspend fun getReviewCount(movieId: Long): Int =
        reviewDao.getReviewCountForMovie(movieId)

    // ===== Network Operations =====

    suspend fun searchMoviesOnline(query: String, page: Int = 1): Result<List<MovieSearchDto>> {
        userPreferencesRepository.setLastSearchQuery(query)
        return networkDataSource.searchMovies(query, page)
    }

    suspend fun getMovieDetailOnline(imdbId: String): Result<com.movievault.app.data.network.dto.MovieDetailDto> {
        return networkDataSource.getMovieDetail(imdbId)
    }

    suspend fun saveMovieFromOnline(imdbId: String): Result<Long> {
        return try {
            val detailResult = networkDataSource.getMovieDetail(imdbId)
            detailResult.fold(
                onSuccess = { detail ->
                    val entity = MovieEntity(
                        title = detail.title,
                        year = detail.year,
                        posterUrl = detail.poster,
                        plot = detail.plot,
                        director = detail.director,
                        actors = detail.actors,
                        genre = detail.genre,
                        imdbRating = detail.imdbRating,
                        runtime = detail.runtime,
                        imdbId = detail.imdbId
                    )
                    val id = movieDao.insertMovie(entity)
                    Result.success(id)
                },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== User Preferences =====

    val themeMode: Flow<String> = userPreferencesRepository.themeMode
    val defaultCategory: Flow<String> = userPreferencesRepository.defaultCategory
    val lastSearchQuery: Flow<String> = userPreferencesRepository.lastSearchQuery
    val isGridViewMode: Flow<Boolean> = userPreferencesRepository.isGridViewMode
    val sortOrder: Flow<String> = userPreferencesRepository.sortOrder

    suspend fun setThemeMode(mode: String) = userPreferencesRepository.setThemeMode(mode)
    suspend fun setDefaultCategory(category: String) = userPreferencesRepository.setDefaultCategory(category)
    suspend fun setLastSearchQuery(query: String) = userPreferencesRepository.setLastSearchQuery(query)
    suspend fun setGridViewMode(isGrid: Boolean) = userPreferencesRepository.setGridViewMode(isGrid)
    suspend fun setSortOrder(order: String) = userPreferencesRepository.setSortOrder(order)
}
