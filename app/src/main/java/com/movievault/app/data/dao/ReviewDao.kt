package com.movievault.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.movievault.app.data.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE movie_id = :movieId ORDER BY created_at DESC")
    fun getReviewsForMovie(movieId: Long): Flow<List<ReviewEntity>>

    @Query("SELECT * FROM reviews WHERE id = :id")
    suspend fun getReviewById(id: Long): ReviewEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity): Long

    @Update
    suspend fun updateReview(review: ReviewEntity)

    @Delete
    suspend fun deleteReview(review: ReviewEntity)

    @Query("DELETE FROM reviews WHERE id = :id")
    suspend fun deleteReviewById(id: Long)

    @Query("SELECT AVG(rating) FROM reviews WHERE movie_id = :movieId")
    suspend fun getAverageRatingForMovie(movieId: Long): Float?

    @Query("SELECT COUNT(*) FROM reviews WHERE movie_id = :movieId")
    suspend fun getReviewCountForMovie(movieId: Long): Int
}
