package com.jencisov.tmdb.domain.managers

import com.jencisov.tmdb.data.services.UpcomingMoviesService
import com.jencisov.tmdb.domain.mappers.UpcomingMoviesMapper
import com.jencisov.tmdb.domain.models.Movie
import io.reactivex.Single

class UpcomingMoviesManager {
    private val upcomingMoviesService: UpcomingMoviesService = UpcomingMoviesService()

    fun getListOfUpcomingMovies(pageNumber: Int): Single<List<Movie>> {
        return upcomingMoviesService.getUpcomingMovies(pageNumber)
            .onErrorResumeNext { throwable -> Single.error(throwable) }
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Single.error(Throwable(response.code().toString()))
                } else Single.just(response)
            }
            .map { response -> response.body() }
            .map { movieResponse -> UpcomingMoviesMapper.Instance.mapList(movieResponse.movies) }
    }

}