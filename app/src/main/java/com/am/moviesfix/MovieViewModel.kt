package com.am.moviesfix

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.am.moviesfix.database.MovieDatabase
import com.am.moviesfix.model.Movie
import com.am.moviesfix.network.RetrofitClient
import com.am.moviesfix.utils.genreMap
import kotlinx.coroutines.launch


class MovieViewModel (application: Application): AndroidViewModel(application) {

    private val movieDao = MovieDatabase.getDatabase(application).movieDao()

    val movies: LiveData<List<Movie>> = movieDao.getAllMovies()
    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults : LiveData<List<Movie>> = _searchResults

    val isLoading = MutableLiveData<Boolean>()

    fun fetchMovies() {

        viewModelScope.launch {
            isLoading.postValue(true)
            try {

                val response =
                    RetrofitClient.instance.getPopularMovies()

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

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun getFavouriteMovies(): LiveData<List<Movie>> {
        return movieDao.getFavouriteMovies(true)
    }

    fun toggleFavourite(search: Movie) {
        viewModelScope.launch {
            movieDao.updateMovie(search.id, !search.isFavourite)
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.searchMovies(query = query)
                val movieList = response.results.map { tmdbMovie ->
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
                _searchResults.postValue(movieList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getMovieById(movieId: Int): LiveData<Movie> {
        return movieDao.getMovieById(movieId)
    }
}
