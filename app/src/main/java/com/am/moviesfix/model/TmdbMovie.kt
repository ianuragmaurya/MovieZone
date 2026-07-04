package com.am.moviesfix.model

import com.google.gson.annotations.SerializedName

data class TmdbMovie (
    val id : Int,
    val title : String,

    @SerializedName("poster_path")
    val posterPath : String,
    @SerializedName("vote_average")
    val rating : Double,

    @SerializedName("genre_ids")
    val genreIds : List<Int>,

    @SerializedName("release_date")
    val releaseDate: String,

    val overview : String
)