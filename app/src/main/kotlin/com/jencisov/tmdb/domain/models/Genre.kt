package com.jencisov.tmdb.domain.models

data class Genre(
        var id: Int?,
        var name: String?
) {
    constructor() : this(null, null)
}