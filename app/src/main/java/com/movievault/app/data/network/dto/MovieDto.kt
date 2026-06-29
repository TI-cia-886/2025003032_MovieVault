package com.movievault.app.data.network.dto

import com.google.gson.annotations.SerializedName

data class OmdbResponse(
    @SerializedName("Search")
    val search: List<MovieSearchDto>? = null,

    @SerializedName("totalResults")
    val totalResults: String? = null,

    @SerializedName("Response")
    val response: String,

    @SerializedName("Error")
    val error: String? = null
)

data class MovieSearchDto(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("imdbID")
    val imdbId: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val poster: String
)

data class MovieDetailDto(
    @SerializedName("Title")
    val title: String = "",

    @SerializedName("Year")
    val year: String = "",

    @SerializedName("Rated")
    val rated: String = "",

    @SerializedName("Released")
    val released: String = "",

    @SerializedName("Runtime")
    val runtime: String = "",

    @SerializedName("Genre")
    val genre: String = "",

    @SerializedName("Director")
    val director: String = "",

    @SerializedName("Writer")
    val writer: String = "",

    @SerializedName("Actors")
    val actors: String = "",

    @SerializedName("Plot")
    val plot: String = "",

    @SerializedName("Language")
    val language: String = "",

    @SerializedName("Country")
    val country: String = "",

    @SerializedName("Awards")
    val awards: String = "",

    @SerializedName("Poster")
    val poster: String = "",

    @SerializedName("imdbRating")
    val imdbRating: String = "",

    @SerializedName("imdbVotes")
    val imdbVotes: String = "",

    @SerializedName("imdbID")
    val imdbId: String = "",

    @SerializedName("Type")
    val type: String = "",

    @SerializedName("Response")
    val response: String = "",

    @SerializedName("Error")
    val error: String? = null
)
