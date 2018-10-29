package com.jencisov.tmdb.app.viewmodel

import com.jencisov.tmdb.app.ui.activity.SplashActivity
import com.jencisov.tmdb.app.ui.viewmodel.SplashViewModel
import com.jencisov.tmdb.domain.managers.MovieGenresManager
import com.jencisov.tmdb.domain.models.Genre
import com.nhaarman.mockito_kotlin.whenever
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class SplashViewModelTest {

    @Mock
    private lateinit var genresManager: MovieGenresManager
    @Mock
    private lateinit var viewModel: SplashViewModel
    @Mock
    private lateinit var splashActivity: SplashActivity

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) = super.scheduleDirect(run, 0, unit)
        override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
    }

    private val genres = listOf(
        Genre(
            1,
            "Drama"
        ),
        Genre(
            2,
            "Action"
        )
    )

    @Before
    fun setupHomeViewModel() {
        MockitoAnnotations.initMocks(this)
        viewModel = SplashViewModel()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
    }

    @Test
    fun onGenresLoad() {
        whenever(genresManager.getListOfMovieGenres()).thenReturn(Single.just(genres))
        viewModel.performInitialLoad(splashActivity)
        verify(viewModel).launchMoviesActiity(genres, splashActivity)
    }

    @Test
    fun onGenresLoadError() {
        whenever(genresManager.getListOfMovieGenres()).thenReturn(Single.error(Exception()))
        viewModel.performInitialLoad(splashActivity)
        verify(splashActivity).displayError()
    }

}