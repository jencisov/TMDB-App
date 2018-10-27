package com.jencisov.tmdb.app.movies.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.MainThread
import com.jencisov.tmdb.BuildConfig
import com.jencisov.tmdb.data.UpcomingMoviesRepository
import com.jencisov.tmdb.data.database.entity.MovieEntity
import com.jencisov.tmdb.data.webservice.response.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingMoviesViewModel(
        private val upcomingMoviesRepository: UpcomingMoviesRepository) : ViewModel() {
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val moviesLiveData: MutableLiveData<Response<MutableList<MovieEntity>>> = MutableLiveData()

    fun isLoading() = loadingLiveData

    @MainThread
    fun upcomingMoviesList(page: Int): LiveData<Response<MutableList<MovieEntity>>> {
        upcomingMoviesRepository.fetchUpcomingMovies(
                BuildConfig.API_KEY, page)
                .map { movies ->
                    movies.sortDescending()
                    movies
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.setValue(true) }
                .doAfterTerminate { loadingLiveData.setValue(false) }
                .subscribe(
                        { movies ->
                            moviesLiveData.value = Response.success(movies)
                        },
                        { throwable ->
                            moviesLiveData.value = Response.error(throwable)
                        }
                )

        return moviesLiveData
    }

}