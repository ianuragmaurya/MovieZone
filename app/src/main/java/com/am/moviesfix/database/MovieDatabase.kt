package com.am.moviesfix.database

import android.content.Context
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.am.moviesfix.model.Movie

@Database(entities = [Movie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {

            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration(true)
                        .build()
            }
        }
    }
}