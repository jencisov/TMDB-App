package com.jencisov.tmdb.domain.mappers

import com.jencisov.tmdb.data.models.GenreDto
import com.jencisov.tmdb.domain.models.Genre
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface MovieGenresMapper {

    companion object {
        val Instance = Mappers.getMapper(MovieGenresMapper::class.java)!!
    }

    fun map(movieGenre: GenreDto?): Genre
    fun mapList(movieGenreList: List<GenreDto>?): List<Genre>

}