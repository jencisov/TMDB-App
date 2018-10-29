package com.jencisov.tmdb.data.models

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
) {
    constructor() : this(null, null)
}