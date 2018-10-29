package com.jencisov.tmdb.domain.managers

import com.jencisov.tmdb.data.services.MovieGenresService
import com.jencisov.tmdb.domain.mappers.MovieGenresMapper
import com.jencisov.tmdb.domain.models.Genre
import io.reactivex.Single

class MovieGenresManager {
    private val movieGenresService: MovieGenresService = MovieGenresService()

    fun getListOfMovieGenres(): Single<List<Genre>> {
        return movieGenresService.getGenres()
            .onErrorResumeNext { throwable -> Single.error(throwable) }
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Single.error(Throwable(response.code().toString()))
                } else Single.just(response)
            }
            .map { response -> response.body() }
            .map { movieGenresResponse -> MovieGenresMapper.Instance.mapList(movieGenresResponse.movieGenres) }
    }

}