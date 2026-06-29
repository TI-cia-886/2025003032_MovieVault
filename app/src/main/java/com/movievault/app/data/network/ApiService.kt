package com.movievault.app.data.network

import com.movievault.app.data.network.dto.MovieDetailDto
import com.movievault.app.data.network.dto.OmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("type") type: String = "movie",
        @Query("page") page: Int = 1
    ): OmdbResponse

    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full"
    ): MovieDetailDto

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com/"

        fun create(): ApiService {
            return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .client(
                    okhttp3.OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val original = chain.request()
                            val url = original.url.newBuilder()
                                .addQueryParameter("apikey", API_KEY)
                                .build()
                            chain.proceed(original.newBuilder().url(url).build())
                        }
                        .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
                            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
                .build()
                .create(ApiService::class.java)
        }

        // OMDb API key - free tier key for demo purposes
        const val API_KEY = "2f064480"
    }
}
