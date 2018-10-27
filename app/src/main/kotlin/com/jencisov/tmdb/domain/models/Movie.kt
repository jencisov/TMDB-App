package com.jencisov.tmdb.domain.models

data class Movie(
        var id: Long?,
        var posterPath: String?,
        var title: String?,
        var releaseDate: String?,
        var overview: String?,
        var genres: List<Int>?) {

    constructor() : this(null, null, null, null, null, null)

}