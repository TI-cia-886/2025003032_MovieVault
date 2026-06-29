package com.movievault.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "poster_url")
    val posterUrl: String,

    @ColumnInfo(name = "plot")
    val plot: String,

    @ColumnInfo(name = "director")
    val director: String,

    @ColumnInfo(name = "actors")
    val actors: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "imdb_rating")
    val imdbRating: String,

    @ColumnInfo(name = "runtime")
    val runtime: String,

    @ColumnInfo(name = "imdb_id")
    val imdbId: String,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "watch_status")
    val watchStatus: String = WatchStatus.WANT_TO_WATCH,

    @ColumnInfo(name = "user_rating")
    val userRating: Float = 0f,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

object WatchStatus {
    const val WANT_TO_WATCH = "want_to_watch"
    const val WATCHING = "watching"
    const val WATCHED = "watched"
}
