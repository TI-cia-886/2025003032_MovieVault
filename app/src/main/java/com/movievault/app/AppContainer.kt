package com.movievault.app

import android.content.Context
import com.movievault.app.data.database.AppDatabase
import com.movievault.app.data.network.ApiService
import com.movievault.app.data.network.NetworkDataSource
import com.movievault.app.data.repository.MovieRepository
import com.movievault.app.datastore.UserPreferencesRepository

class AppContainer(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val apiService = ApiService.create()
    private val networkDataSource = NetworkDataSource(apiService)

    val movieDao = database.movieDao()
    val reviewDao = database.reviewDao()

    val userPreferencesRepository = UserPreferencesRepository(context)

    val movieRepository = MovieRepository(
        movieDao = movieDao,
        reviewDao = reviewDao,
        networkDataSource = networkDataSource,
        userPreferencesRepository = userPreferencesRepository
    )
}
