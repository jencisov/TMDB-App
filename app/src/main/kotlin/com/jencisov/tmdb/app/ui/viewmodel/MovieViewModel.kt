package com.jencisov.tmdb.app.ui.viewmodel

import com.jencisov.tmdb.app.ui.base.BasePaginationViewModel
import com.jencisov.tmdb.data.pagination.datasource.UpcomingMoviesDataSource
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.data.pagination.factory.UpcomingMoviesDataSourceFactory

class MovieViewModel : BasePaginationViewModel<UpcomingMoviesDataSourceFactory, Movie>() {

    init {
        dataSourceFactory = UpcomingMoviesDataSourceFactory(getListener())
    }

    override fun getPageSize(): Int = 20

    fun performInitialLoad() {
        dataSourceFactory
    }

    fun resetMoviesDownloadingCurrentPage(){
        UpcomingMoviesDataSource.CURRENT_PAGE = 1
    }

}