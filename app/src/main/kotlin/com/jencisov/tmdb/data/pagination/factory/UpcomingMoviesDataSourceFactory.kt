package com.jencisov.tmdb.data.pagination.factory

import android.arch.paging.DataSource
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.data.pagination.datasource.UpcomingMoviesDataSource
import com.jencisov.tmdb.data.pagination.datasource._base.OnDataSourceLoading

class UpcomingMoviesDataSourceFactory(var loading: OnDataSourceLoading) : DataSource.Factory<Int, Movie>() {
    lateinit var source: UpcomingMoviesDataSource

    override fun create(): DataSource<Int, Movie>? {
        if (::source.isInitialized && source != null) source.invalidate()
        source = UpcomingMoviesDataSource()
        source.onDataSourceLoading = loading
        return source
    }

}