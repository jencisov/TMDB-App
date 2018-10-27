package com.jencisov.tmdb.data.models

import com.google.gson.annotations.SerializedName

data class MovieDto(
        @SerializedName("id")
        var id: Long?,
        @SerializedName("poster_path")
        var posterPath: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("release_date")
        var releaseDate: String?,
        @SerializedName("overview")
        var overview: String?,
        @SerializedName("genre_ids")
        var genres: List<Int>?) {

    constructor() : this(null, null, null, null, null, null)

}