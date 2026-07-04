package com.am.moviesfix

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.am.moviesfix.database.MovieDatabase
import com.am.moviesfix.network.RetrofitClient
import com.am.moviesfix.repository.MovieRepository

class MovieViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun<T: ViewModel> create(modelClass: Class<T>): T {

        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        val apiService = RetrofitClient.instance
        val repository = MovieRepository(apiService, movieDao)

        @Suppress("UNCHECKED_CAST")
        return MovieViewModel(repository) as T
    }
}