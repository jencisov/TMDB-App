package com.jencisov.tmdb.app.movies.viewmodel

import android.arch.lifecycle.ViewModel
import com.jencisov.tmdb.data.UpcomingMoviesRepository
import com.jencisov.tmdb.data.database.dao.MovieDao
import com.jencisov.tmdb.data.webservice.TheMovieDbService

internal class UpcomingMoviesViewModelFactory(
        moviesService: TheMovieDbService,
        moviesDao: MovieDao
) : BaseViewModelFactory() {

    private val repository: UpcomingMoviesRepository by lazy { UpcomingMoviesRepository(moviesService, moviesDao) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == UpcomingMoviesViewModel::class.java) {
            return UpcomingMoviesViewModel(repository) as T
        }
        return super.create(modelClass)
    }

}