package com.am.moviesfix.network

import com.am.moviesfix.BuildConfig
import com.am.moviesfix.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")

    suspend fun getPopularMovies(
        @Query ("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieResponse

    @GET ("search/movie")

    suspend fun searchMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("query") query: String
    ): MovieResponse

}