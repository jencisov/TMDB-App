package com.jencisov.tmdb.data.response

import com.google.gson.annotations.SerializedName
import com.jencisov.tmdb.data.models.GenreDto

class MovieGenresResponse {

    @SerializedName("genres")
    lateinit var movieGenres: List<GenreDto>

}