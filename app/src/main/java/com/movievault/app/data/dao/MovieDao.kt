package com.movievault.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.movievault.app.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY created_at DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Long): MovieEntity?

    @Query("SELECT * FROM movies WHERE imdb_id = :imdbId LIMIT 1")
    suspend fun getMovieByImdbId(imdbId: String): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovieById(id: Long)

    @Query("SELECT * FROM movies WHERE is_favorite = 1 ORDER BY updated_at DESC")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE watch_status = :status ORDER BY updated_at DESC")
    fun getMoviesByWatchStatus(status: String): Flow<List<MovieEntity>>

    @Query(
        "SELECT * FROM movies WHERE title LIKE '%' || :query || '%' " +
        "OR director LIKE '%' || :query || '%' " +
        "OR actors LIKE '%' || :query || '%' " +
        "ORDER BY created_at DESC"
    )
    fun searchMovies(query: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE genre LIKE '%' || :genre || '%' ORDER BY created_at DESC")
    fun getMoviesByGenre(genre: String): Flow<List<MovieEntity>>



    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT COUNT(*) FROM movies WHERE watch_status = :status")
    suspend fun getMovieCountByStatus(status: String): Int

    @Query("SELECT genre FROM movies")
    suspend fun getAllGenres(): List<String>
}
