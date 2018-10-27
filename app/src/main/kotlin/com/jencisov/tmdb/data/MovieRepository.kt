package com.jencisov.tmdb.data

import com.jencisov.tmdb.data.database.dao.MovieDao
import com.jencisov.tmdb.data.database.entity.MovieEntity
import com.jencisov.tmdb.data.webservice.TheMovieDbService
import com.jencisov.tmdb.data.webservice.response.MoviesResponse
import io.reactivex.Observable

class UpcomingMoviesRepository(private val service: TheMovieDbService, private val moviesDao: MovieDao) {

    fun fetchUpcomingMovies(apiKey: String, page: Int): Observable<MutableList<MovieEntity>> {
        return service.getUpcomingMovies(apiKey, page)
                .map(MoviesResponse::movies)
                .doOnNext { movies ->
                    moviesDao.insertAll(movies)
                }
                .onErrorResumeNext { error: Throwable ->
                    Observable.just(moviesDao.loadAllMovies())
                }
    }

}