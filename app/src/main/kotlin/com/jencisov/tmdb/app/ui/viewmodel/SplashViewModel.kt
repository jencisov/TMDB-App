package com.jencisov.tmdb.app.ui.viewmodel

import android.arch.lifecycle.ViewModel
import com.jencisov.tmdb.app.ui.activity.MoviesActivity
import com.jencisov.tmdb.app.ui.activity.SplashActivity
import com.jencisov.tmdb.app.utils.Genres
import com.jencisov.tmdb.domain.managers.MovieGenresManager
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
                            Genres.initGenresHashMap(items)
                            MoviesActivity.launchActivity(activity)
                            activity.finish()
                        },
                        { activity.displayError() }
                )
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}