package com.jencisov.tmdb.app.utils

import com.jencisov.tmdb.domain.models.Genre

object Genres {
    private val genresMap = HashMap<Int, String>()

    fun getGenreById(id: Int): String? {
        return genresMap.get(id) ?: return ""
    }

    fun initGenresHashMap(genreList: List<Genre>) {
        for (genre in genreList) {
            genresMap.put(genre.id!!, genre.name!!)
        }
    }

}