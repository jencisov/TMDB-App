package com.jencisov.tmdb.domain.mappers

import com.jencisov.tmdb.data.models.MovieDto
import com.jencisov.tmdb.domain.models.Movie
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UpcomingMoviesMapper {

    companion object {
        val Instance = Mappers.getMapper(UpcomingMoviesMapper::class.java)!!
    }

    fun map(movie: MovieDto?): Movie
    fun mapList(movieList: List<MovieDto>?): List<Movie>

}