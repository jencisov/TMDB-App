package com.jencisov.tmdb.data.pagination.datasource

import android.annotation.SuppressLint
import com.jencisov.tmdb.domain.managers.ReposManager
import com.jencisov.tmdb.domain.models.Movie
import com.jencisov.tmdb.data.pagination.datasource._base.BaseDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingMoviesDataSource : BaseDataSource<Movie>() {

    companion object {
        var CURRENT_PAGE = 1;
    }

    val manager = ReposManager()

    @SuppressLint("CheckResult")
    override fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        manager.getListOfRepos(CURRENT_PAGE++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                        { items -> submitInitialData(items, params, callback) },
                        { error -> submitInitialError(error) }
                )
    }

    @SuppressLint("CheckResult")
    override fun loadAdditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        manager.getListOfRepos(CURRENT_PAGE++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                        { items -> submitData(items, params, callback) },
                        { error -> submitError(error) }
                )
    }

}