package com.jencisov.tmdb.app.ui.viewmodel

import android.arch.lifecycle.ViewModel
import com.jencisov.tmdb.app.ui.activity.MoviesActivity
import com.jencisov.tmdb.app.ui.activity.SplashActivity
import com.jencisov.tmdb.app.utils.Genres
import com.jencisov.tmdb.domain.managers.MovieGenresManager
import com.jencisov.tmdb.domain.models.Genre
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SplashViewModel : ViewModel() {

    private val movieGenresManager: MovieGenresManager = MovieGenresManager()
    private var compositeDisposable = CompositeDisposable()

    fun performInitialLoad(activity: SplashActivity) {
        activity.hideError()

        movieGenresManager.getListOfMovieGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::addDisposable)
                .subscribe(
                        { items ->
                            launchMoviesActiity(items, activity)
                        },
                        { activity.displayError() }
                )
    }

    fun launchMoviesActiity(
        items: List<Genre>,
        activity: SplashActivity
    ) {
        Genres.initGenresHashMap(items)
        MoviesActivity.launchActivity(activity)
        activity.finish()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}