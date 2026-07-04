package com.am.moviesfix.repository

import androidx.lifecycle.LiveData
import com.am.moviesfix.database.MovieDao
import com.am.moviesfix.model.Movie
import com.am.moviesfix.network.ApiService
import com.am.moviesfix.utils.genreMap

class MovieRepository(
       private val apiService: ApiService,
       private val movieDao: MovieDao
) {

       fun getAllMovies(): LiveData<List<Movie>> {
              return movieDao.getAllMovies()
       }

       fun getFavouriteMovies(): LiveData<List<Movie>> {
              return movieDao.getFavouriteMovies(true)
       }

       fun getMovieById(movieId: Int): LiveData<Movie> {
              return movieDao.getMovieById(movieId)
       }

       suspend fun toggleFavourite(movie: Movie) {
              movieDao.updateMovie(movie.id, !movie.isFavourite)
       }

       suspend fun searchMovies(query: String): List<Movie> {

              val response = apiService.searchMovies(query = query)
              return response.results.map { tmdbMovie ->
                     Movie(
                            id = tmdbMovie.id,
                            title = tmdbMovie.title,
                            category = genreMap[tmdbMovie.genreIds.firstOrNull()] ?: "Unknown",
                            posterUrl = "https://image.tmdb.org/t/p/w500${tmdbMovie.posterPath}",
                            rating = tmdbMovie.rating,
                            overview = tmdbMovie.overview,
                            releaseDate = tmdbMovie.releaseDate,
                            isFavourite = false
                     )
              }
       }
       suspend fun refreshMovies() {
              val response = apiService.getPopularMovies()

              val existingMovies = movieDao.getAllMoviesOnce()

              val movieList = response.results.map { tmdbMovie ->
                     val existing = existingMovies.find { it.id == tmdbMovie.id }

                     Movie(
                            id = tmdbMovie.id,
                            title = tmdbMovie.title,
                            category = genreMap[tmdbMovie.genreIds.firstOrNull()] ?: "Unknown",
                            posterUrl = "https://image.tmdb.org/t/p/w500${tmdbMovie.posterPath}",
                            rating = tmdbMovie.rating,
                            overview = tmdbMovie.overview,
                            releaseDate = tmdbMovie.releaseDate,
                            isFavourite = existing?.isFavourite ?: false
                     )
              }

                movieDao.insertMovies(movieList)
       }
}