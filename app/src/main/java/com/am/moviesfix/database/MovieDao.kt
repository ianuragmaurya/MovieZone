package com.am.moviesfix.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.am.moviesfix.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies")
    suspend fun getAllMoviesOnce(): List<Movie>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies WHERE isFavourite =:isFavourite")
    fun getFavouriteMovies(isFavourite: Boolean): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int) : LiveData<Movie>

@Query("UPDATE movies SET isFavourite = :isFavourite WHERE id =:movieId")

   suspend fun updateMovie(movieId: Int, isFavourite: Boolean)
}