package com.jencisov.tmdb.domain.mappers

import com.jencisov.tmdb.data.models.MovieDto
import com.jencisov.tmdb.domain.models.Movie
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface ReposMapper {

    companion object {
        val Instance = Mappers.getMapper(ReposMapper::class.java)!!
    }

    fun map(repos: MovieDto?): Movie
    fun mapList(repos: List<MovieDto>?): List<Movie>

}