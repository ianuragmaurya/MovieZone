package com.am.moviesfix.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")

data class Movie(
    @PrimaryKey
    val id: Int,
    val title : String,
    val category: String,
    val posterUrl: String,
    val rating: Double,
    val overview: String,
    val isFavourite: Boolean = false,
    val releaseDate: String = ""
)