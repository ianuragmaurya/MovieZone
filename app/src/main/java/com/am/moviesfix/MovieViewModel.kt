package com.am.moviesfix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.moviesfix.model.Movie
import com.am.moviesfix.repository.MovieRepository
import kotlinx.coroutines.launch


class MovieViewModel (private val repository: MovieRepository) : ViewModel() {

    val movies: LiveData<List<Movie>> = repository.getAllMovies()
    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    val isLoading = MutableLiveData<Boolean>()

    fun fetchMovies() {

        viewModelScope.launch {
            isLoading.postValue(true)
            try {

                repository.refreshMovies()

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun getFavouriteMovies(): LiveData<List<Movie>> {
        return repository.getFavouriteMovies()
    }

    fun getMovieById(movieId: Int): LiveData<Movie> {
        return repository.getMovieById(movieId)
    }

    fun toggleFavourite(search: Movie) {
        viewModelScope.launch {
            repository.toggleFavourite(search)


        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {

                val movieList = repository.searchMovies(query)

                _searchResults.postValue(movieList)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
