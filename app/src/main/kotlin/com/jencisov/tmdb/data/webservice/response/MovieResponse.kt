package com.jencisov.tmdb.data.webservice.response

import com.google.gson.annotations.SerializedName
import com.jencisov.tmdb.data.database.entity.MovieEntity

class MoviesResponse {

    @SerializedName("results")
    lateinit var movies: MutableList<MovieEntity>

}