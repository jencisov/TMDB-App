package com.jencisov.tmdb.data.response

import com.google.gson.annotations.SerializedName
import com.jencisov.tmdb.data.models.MovieDto

class MoviesResponse {

    @SerializedName("results")
    lateinit var movies: MutableList<MovieDto>

}