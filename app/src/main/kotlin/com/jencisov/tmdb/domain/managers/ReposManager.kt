package com.jencisov.tmdb.domain.managers

import com.jencisov.tmdb.data.services.MovieService
import com.jencisov.tmdb.domain.mappers.ReposMapper
import com.jencisov.tmdb.domain.models.Movie
import io.reactivex.Single

class ReposManager {
    private val movieService: MovieService = MovieService()

    fun getListOfRepos(pageNumber: Int): Single<List<Movie>> {
        return movieService.getReposForUser(pageNumber)
            .onErrorResumeNext({ throwable -> Single.error(throwable) })
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Single.error(Throwable(response.code().toString()))
                } else Single.just(response)
            }
            .map { response -> response.body() }
            .map { movieResponse -> ReposMapper.Instance.mapList(movieResponse.movies) }
    }

}